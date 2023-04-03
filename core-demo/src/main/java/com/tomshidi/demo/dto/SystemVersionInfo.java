package com.tomshidi.demo.dto;

/**
 * @author tangshili
 * @since 2023/3/29 16:55
 */
public class SystemVersionInfo {
    private String versionId;

    private String author;

    private String email;

    private String desc;

    public String getVersionId() {
        return versionId;
    }

    public SystemVersionInfo versionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public SystemVersionInfo author(String author) {
        this.author = author;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SystemVersionInfo email(String email) {
        this.email = email;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public SystemVersionInfo desc(String desc) {
        this.desc = desc;
        return this;
    }
}
