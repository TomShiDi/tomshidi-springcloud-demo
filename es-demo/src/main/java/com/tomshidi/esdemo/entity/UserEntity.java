package com.tomshidi.esdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author tomshidi
 * @date 2022/11/23 15:54
 */
@Document(indexName = "user_data")
public class UserEntity implements Serializable {

    @Id
    private String userId;

    @Field(name = "user_name", type = FieldType.Keyword)
    private String userName;

    @Field(name = "age", type = FieldType.Integer)
    private int age;

    private String address;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
