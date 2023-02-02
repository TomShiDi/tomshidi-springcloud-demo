package com.tomshidi.demo.dto;

/**
 * @author tomshidi
 * @date 2021/10/11 18:19
 */
public class AccountEntity {
    private String number;

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
