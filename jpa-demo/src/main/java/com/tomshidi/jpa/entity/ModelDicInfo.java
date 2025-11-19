package com.tomshidi.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * 模型目录信息
 * @author tomshidi
 * @since 2024/4/17 10:12
 */
@Entity(name = "model_dic_info")
public class ModelDicInfo {
    @Id
    private String id;

    @Column(name = "dic_name")
    private String dicName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }
}
