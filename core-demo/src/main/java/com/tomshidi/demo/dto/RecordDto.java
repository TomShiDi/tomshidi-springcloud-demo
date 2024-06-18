package com.tomshidi.demo.dto;

/**
 * @author tangshili
 * @since 2024/6/15 16:20
 */
public class RecordDto {
    private String userKey;

    private String userValue;

    private String checkTimeKey;

    private String checkTimeValue;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserValue() {
        return userValue;
    }

    public void setUserValue(String userValue) {
        this.userValue = userValue;
    }

    public String getCheckTimeKey() {
        return checkTimeKey;
    }

    public void setCheckTimeKey(String checkTimeKey) {
        this.checkTimeKey = checkTimeKey;
    }

    public String getCheckTimeValue() {
        return checkTimeValue;
    }

    public void setCheckTimeValue(String checkTimeValue) {
        this.checkTimeValue = checkTimeValue;
    }
}
