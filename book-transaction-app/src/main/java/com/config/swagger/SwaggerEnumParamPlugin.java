package com.config.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ModelSpecificationBuilder;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.schema.ScalarTypes;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.DescriptionResolver;

import java.util.*;
import java.util.stream.Collectors;

import static springfox.documentation.swagger.common.SwaggerPluginSupport.OAS_PLUGIN_ORDER;

/**
 *
 * ModelPropertyBuilderPlugin 适用于 application/json 提交的请求参数 及 json返回值 注释
 * ExpandedParameterBuilderPlugin 适用于POJO/VO内的枚举参数（表单形式生成对象，或get方式）
 * ParameterBuilderPlugin、OperationBuilderPlugin 适用于请求方法上的直接枚举参数
 *
 * https://blog.csdn.net/hy6533/article/details/126178825
 */

@Configuration
@RequiredArgsConstructor
@Slf4j
@Order(SwaggerEnumObjAttrPlugin.ENUM_PLUGIN_ORDER)
public class SwaggerEnumParamPlugin extends SwaggerEnumParser implements ParameterBuilderPlugin, OperationBuilderPlugin {
    public static final int ENUM_PLUGIN_ORDER = OAS_PLUGIN_ORDER + 2;
    private final TypeResolver resolver;
    private final DescriptionResolver descriptions;

    /**
     * 方法级别的操作或适合参数的增减
     * @param context
     */
    @Override
    public void apply(OperationContext context) {
        //适合参数的增减
    }

    /**
     * 枚举直接当作参数
     * @param context
     */
    @Override
    public void apply(ParameterContext context) {
        //获取枚举字段:包话泛型，如：List<StatusEnum>
        List<Class> classList = new ArrayList<>();
        classList.add(context.resolvedMethodParameter().getParameterType().getErasedType());
        classList.addAll(
                Optional.ofNullable(context.resolvedMethodParameter().getParameterType().getTypeParameters()).orElse(Collections.emptyList())
                        .stream().map(ResolvedType::getErasedType).collect(Collectors.toList())
        );
        Class erasedType = classList.stream().filter(e->super.matchType(e)).findAny().orElse(null);
        if(erasedType == null){
            return;
        }
        Optional<ApiParam> apiParamOptional = context.resolvedMethodParameter().findAnnotation(ApiParam.class);
        String allowableProperty = apiParamOptional.map(ApiParam::allowableValues).orElse("");
        String description = descriptions.resolve(apiParamOptional.map(ApiParam::value).orElse(""));
        extracted(context,allowableProperty,erasedType,description);
    }

    private void extracted(ParameterContext context, String allowableProperty, Class<?> erasedType, String description) {
        description = String.format("%s[%s]",description,keyValueStrJoinResult(erasedType));
        //枚举值类型
        ResolvedType resolved = getBodyEnumResolvedType(resolver, erasedType);
        ModelSpecification modelSpecification = null;
        if (Objects.nonNull(resolved)) {
            modelSpecification = new ModelSpecificationBuilder()
                    .scalarModel(ScalarTypes.builtInScalarType(resolved)
                            .orElse(ScalarType.STRING))
                    .build();
        }
        AllowableValues allowable = getAllowableValues(allowableProperty, erasedType);
        //Extensions
        Map<String,String> name2CodeMap = name2CodeMap(erasedType);
        ObjectVendorExtension objectExtension = new ObjectVendorExtension("fields");
        name2CodeMap.forEach((key,value)->objectExtension.addProperty(new StringVendorExtension(key,value)));
        ModelSpecification modelSpecificationFinal = modelSpecification;
        context.requestParameterBuilder()
                .description(description)
                .precedence(ENUM_PLUGIN_ORDER)
                .extensions(Arrays.asList(objectExtension))
                .query(q -> q.enumerationFacet(e -> e.allowedValues(allowable))
                        .model(mb -> mb.copyOf(modelSpecificationFinal)));
        context.parameterBuilder()
                .description(description)
                .allowableValues(allowable)
                .order(ENUM_PLUGIN_ORDER)
                .vendorExtensions(Arrays.asList(objectExtension))
                .build();
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
