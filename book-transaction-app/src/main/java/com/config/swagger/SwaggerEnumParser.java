package com.config.swagger;

import com.babycare.common.validator.component.IEnum;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableRangeValues;
import springfox.documentation.service.AllowableValues;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author wangsp
 * @since 2022/5/24
 */
@Slf4j
public class SwaggerEnumParser {

    protected boolean matchType(Class<?> rawType){
        return rawType.isEnum() && IEnum.class.isAssignableFrom(rawType);
    }

    protected String keyValueStrJoinResult(Class<?> rawType) {
        return keyValueStrList(rawType).stream().collect(Collectors.joining(","));
    }

    protected List<String> keyValueStrList(final Class<?> clazz) {
        List<String> list = new ArrayList<>(clazz.getEnumConstants().length);
        for (Object enumConstant : clazz.getEnumConstants()) {
            IEnum e = (IEnum) enumConstant;
            list.add(e.getCode() + ":" + e.getDesc());
        }
        return list;
    }

    protected Map<String, String> name2CodeMap(Class<?> rawType) {
        Map<String,String> map = new HashMap<>(rawType.getEnumConstants().length);
        for (Object enumConstant : rawType.getEnumConstants()) {
            IEnum e = (IEnum) enumConstant;
            map.put(String.valueOf(enumConstant),String.valueOf(e.getCode()));
        }
        return map;
    }

    /**
     * 根据IEnum的code类型判断枚举传值类型
     * @param resolver
     * @param erasedType
     * @return
     */
    public ResolvedType getBodyEnumResolvedType(TypeResolver resolver, Class<?> erasedType) {
        ResolvedType resolved = null;
        Type[] genericInterfaces = erasedType.getGenericInterfaces();
        if (genericInterfaces.length < 1) {
            return null;
        }
        if(matchType(erasedType)){
            try {
                return resolver.resolve(erasedType.getDeclaredField("code").getType());
            } catch (NoSuchFieldException e) {
                return null;
            }
        }
        //泛型类型来设置枚举类型，貌似用不到
        if(!ParameterizedType.class.isAssignableFrom(genericInterfaces[0].getClass())){
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length > 1) {
            Type actualTypeArgument = actualTypeArguments[1];
            resolved = Optional.of(resolver.resolve(actualTypeArgument)).orElse(null);
        }
        return resolved;
    }

    @SuppressWarnings("java:S4784")
    private static final Pattern RANGE_PATTERN = Pattern.compile("range([\\[(])(.*),(.*)([])])$");

    protected AllowableValues getAllowableValues(String allowableValueString, Class<?> rawType) {
        List<String> strings = new ArrayList<>();
        String trimmed = Optional.ofNullable(allowableValueString).orElse("").trim();
        Matcher matcher = RANGE_PATTERN.matcher(trimmed.replaceAll(" ", ""));
        if (matcher.matches()) {
            if (matcher.groupCount() != 4) {
                log.warn("Unable to parse range specified {} correctly", trimmed);
            } else {
                return new AllowableRangeValues(
                        matcher.group(2).contains("infinity") ? null : matcher.group(2),
                        matcher.group(1).equals("("),
                        matcher.group(3).contains("infinity") ? null : matcher.group(3),
                        matcher.group(4).equals(")"));
            }
        } else if (trimmed.contains(",")) {
            strings = Stream.of(trimmed.split(",")).map(String::trim).filter(item -> !item.isEmpty()).collect(toList());
        } else if (hasText(trimmed)) {
            strings = singletonList(trimmed);
        } else if (matchType(rawType)){
            strings = Arrays.stream(rawType.getEnumConstants()).map(e->((IEnum) e).getCode().toString()).collect(toList());
        }
        return new AllowableListValues(strings, "LIST");
    }
}