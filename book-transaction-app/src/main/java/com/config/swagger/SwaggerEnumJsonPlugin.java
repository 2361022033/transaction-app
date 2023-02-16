package com.config.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JavaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.property.ModelSpecificationFactory;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;
import static springfox.documentation.schema.Annotations.findPropertyAnnotation;
import static springfox.documentation.swagger.schema.ApiModelProperties.findApiModePropertyAnnotation;


/**
 * ApiModelProperty解析枚举键值插件
 *
 * @Author: wangsp
 * @Date: 2021/5/12
 */
@Configuration
@Order(SwaggerPluginSupport.OAS_PLUGIN_ORDER)
@RequiredArgsConstructor
@Slf4j
public class SwaggerEnumJsonPlugin extends SwaggerEnumParser implements ModelPropertyBuilderPlugin {
    private final ModelSpecificationFactory modelSpecifications;
    private static final String FIELD_DESCRIPTION = "description";

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> ann = Optional.empty();
        if (context.getAnnotatedElement().isPresent()) {
            ann = ann.map(Optional::of)
                            .orElse(findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            ann = ann.map(Optional::of).orElse(findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
        }
        if (!ann.isPresent()) {
            return;
        }
        //获取枚举字段:包话泛型，如：List<StatusEnum>
        Class<?> fieldType = context.getBeanPropertyDefinition().get().getField().getRawType();
        List<JavaType> typeParameters = context.getBeanPropertyDefinition().get().getField().getType().getBindings().getTypeParameters();
        List<Class> classList = new ArrayList<>();
        classList.add(fieldType);
        classList.addAll(
                Optional.ofNullable(typeParameters).orElse(Collections.emptyList())
                        .stream().map(JavaType::getRawClass).collect(Collectors.toList())
        );
        Class<?> rawType = classList.stream().filter(e->super.matchType(e)).findAny().orElse(null);
        if(rawType == null){
            return;
        }
        /*if (modelSpecifications.getEnumTypeDeterminer().isEnum(rawType)) {
        }*/
        String displayValue = keyValueStrJoinResult(rawType);
        // description
        Field description = ReflectionUtils.findField(PropertySpecificationBuilder.class, FIELD_DESCRIPTION);
        description.setAccessible(true);
        String descriptionValue = (String) ReflectionUtils.getField(description, context.getSpecificationBuilder());
        displayValue = Objects.nonNull(descriptionValue) ? String.format("%s[%s]",descriptionValue,displayValue) : displayValue;
        ModelSpecification modelSpecification =
                ann.map(a -> {
                            if (!a.dataType().isEmpty()) {
                                return modelSpecifications
                                        .create(context.getOwner(), toType(context.getResolver()).apply(a));
                            }
                            ResolvedType resolvedType = getBodyEnumResolvedType(context.getResolver(), rawType);
                            if (Objects.nonNull(resolvedType)) {
                                return modelSpecifications
                                        .create(context.getOwner(), resolvedType);
                            }
                            return null;
                        })
                        .orElse(null);
        String allowableValues = ann.map(ApiModelProperty::allowableValues).orElse(null);
        //Extensions
        Map<String,String> name2CodeMap = name2CodeMap(rawType);
        ObjectVendorExtension objectExtension = new ObjectVendorExtension("fields");
        name2CodeMap.forEach((key,value)->objectExtension.addProperty(new StringVendorExtension(key,value)));
        context.getSpecificationBuilder().description(displayValue)
                .type(modelSpecification)
                .readOnly(ann.map(ApiModelProperty::readOnly).orElse(false))
                .isHidden(ann.map(ApiModelProperty::hidden).orElse(false))
                .position(ann.map(ApiModelProperty::position).orElse(0))
                .required(ann.map(ApiModelProperty::required).orElse(false))
                .example(ann.map(toExample()).orElse(""))
                .enumerationFacet(e -> e.allowedValues(super.getAllowableValues(allowableValues,rawType)))
                .vendorExtensions(Arrays.asList(objectExtension));

        context.getBuilder().description(displayValue)
                .type(ann.map(a -> {
                    ResolvedType resolvedType = getBodyEnumResolvedType(context.getResolver(), rawType);
                    if (Objects.nonNull(resolvedType)) {
                        return resolvedType;
                    }
                    return toType(context.getResolver()).apply(a);
                }).orElse(null))
                .allowableValues(super.getAllowableValues(allowableValues,rawType))
                .required(ann.map(ApiModelProperty::required).orElse(false))
                .readOnly(ann.map(ApiModelProperty::readOnly).orElse(false))
                .isHidden(ann.map(ApiModelProperty::hidden).orElse(false))
                .position(ann.map(ApiModelProperty::position).orElse(0))
                .example(ann.map(toExample()).orElse(""))
                .extensions(Arrays.asList(objectExtension));
    }

    static Function<ApiModelProperty, ResolvedType> toType(final TypeResolver resolver) {
        return annotation -> {
            try {
                return resolver.resolve(Class.forName(annotation.dataType()));
            } catch (ClassNotFoundException e) {
                return resolver.resolve(Object.class);
            }
        };
    }

    static Function<ApiModelProperty, String> toExample() {
        return annotation -> {
            String example = "";
            if (!isEmpty(annotation.example())) {
                example = annotation.example();
            }
            return example;
        };
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return SwaggerPluginSupport.pluginDoesApply(documentationType);
    }
}