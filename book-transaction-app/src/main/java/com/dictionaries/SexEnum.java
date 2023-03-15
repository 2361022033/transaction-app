package com.dictionaries;

public enum SexEnum {

    /**
     * 男
     */
    MAN("男"),

    /**
     * 女
     */
    WOMAN("女");

    private final String value;

    SexEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


