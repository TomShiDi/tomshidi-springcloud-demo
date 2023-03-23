package com.tomshidi.demo.dto;

import com.tomshidi.base.encrypt.annotation.Encrypt;

/**
 * @author tomshidi
 * @date 2021/10/11 18:19
 */
public class AccountEntity {

    @Encrypt
    private String number;

    @Encrypt
    private String password;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "number='" + number + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
