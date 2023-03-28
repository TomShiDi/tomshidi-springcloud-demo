package com.tomshidi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tomshidi.base.encrypt.annotation.Encrypt;

import java.time.LocalDateTime;

/**
 * @author tomshidi
 * @since 2023/3/24 17:45
 */
@TableName("person_info")
public class PersonInfoEntity {
    @TableId
    private String id;

    @TableField(value = "name")
    @Encrypt
    private String name;

    @Encrypt
    private String sex;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "hire_date")
    private LocalDateTime hireDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }
}
