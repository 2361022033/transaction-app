package com.config.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModelProperty;
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
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.OAS_PLUGIN_ORDER;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Order(SwaggerEnumObjAttrPlugin.ENUM_PLUGIN_ORDER)
public class SwaggerEnumObjAttrPlugin extends SwaggerEnumParser implements ExpandedParameterBuilderPlugin {
    private final TypeResolver resolver;
    private final DescriptionResolver descriptions;
    private final EnumTypeDeterminer enumTypeDeterminer;
    public static final int ENUM_PLUGIN_ORDER = OAS_PLUGIN_ORDER + 2;

    @Override
    public void apply(ParameterExpansionContext context) {
        Optional<ApiModelProperty> apiModelPropertyOptional = context.findAnnotation(ApiModelProperty.class);
        apiModelPropertyOptional.ifPresent(apiModelProperty -> fromApiModelProperty(context, apiModelProperty));
        Optional<ApiParam> apiParamOptional = context.findAnnotation(ApiParam.class);
        apiParamOptional.ifPresent(apiParam -> fromApiParam(context, apiParam));
    }

    private void fromApiModelProperty(
            ParameterExpansionContext context,
            ApiModelProperty apiModelProperty) {
        String allowableProperty = ofNullable(apiModelProperty.allowableValues())
                .filter(((Predicate<String>) String::isEmpty).negate()).orElse(null);
        //获取枚举字段:包话泛型，如：List<StatusEnum>
        List<Class> classList = new ArrayList<>();
        classList.add(context.getFieldType().getErasedType());
        classList.addAll(
                Optional.ofNullable(context.getFieldType().getTypeParameters()).orElse(Collections.emptyList())
                        .stream().map(ResolvedType::getErasedType).collect(Collectors.toList())
        );
        Class erasedType = classList.stream().filter(e->super.matchType(e)).findAny().orElse(null);
        if(erasedType == null){
            return;
        }
        //枚举值描述
        String description = descriptions.resolve(apiModelProperty.value());
        extracted(context, allowableProperty, erasedType, description);
    }

    private void extracted(ParameterExpansionContext context, String allowableProperty, Class<?> erasedType, String description) {
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
        context.getParameterBuilder()
                .description(description)
                .allowableValues(allowable)
                .order(ENUM_PLUGIN_ORDER)
                .vendorExtensions(Arrays.asList(objectExtension))
                .build();

        context.getRequestParameterBuilder()
                .description(description)
                .precedence(ENUM_PLUGIN_ORDER)
                .extensions(Arrays.asList(objectExtension))
                .query(q -> q.enumerationFacet(e -> e.allowedValues(allowable))
                        .model(mb -> mb.copyOf(modelSpecificationFinal)));
    }

    private void fromApiParam(
            ParameterExpansionContext context,
            ApiParam apiParam) {
        String allowableProperty =
                ofNullable(apiParam.allowableValues())
                        .filter(((Predicate<String>) String::isEmpty).negate())
                        .orElse(null);
        List<Class> classList = new ArrayList<>();
        classList.add(context.getFieldType().getErasedType());
        classList.addAll(
                Optional.ofNullable(context.getFieldType().getTypeParameters()).orElse(Collections.emptyList())
                        .stream().map(ResolvedType::getErasedType).collect(Collectors.toList())
        );
        Class erasedType = classList.stream().filter(e->super.matchType(e)).findAny().orElse(null);
        if(erasedType == null){
            return;
        }
        String description = descriptions.resolve(apiParam.value());
        extracted(context,allowableProperty,erasedType,description);
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return SwaggerPluginSupport.pluginDoesApply(documentationType);
    }
}