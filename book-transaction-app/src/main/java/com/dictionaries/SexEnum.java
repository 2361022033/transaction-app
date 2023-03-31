package com.dictionaries;

public enum SexEnum {

    /**
     * 男
     */
    MAN(1, "男"),

    /**
     * 女
     */
    WOMAN(0, "女");

    private final Integer code;
    private final String desc;

    SexEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}


