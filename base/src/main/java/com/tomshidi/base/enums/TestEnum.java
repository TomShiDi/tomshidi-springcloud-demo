package com.tomshidi.base.enums;

/**
 * @author tomshidi
 * @since 2023/2/24 11:10
 */
public enum TestEnum {
    /**
     *
     */
    TYPE_ADMIN("admin"),
    TYPE_USER("user"),
    ;

    private String param;

    TestEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
