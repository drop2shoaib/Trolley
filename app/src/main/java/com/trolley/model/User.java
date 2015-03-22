package com.trolley.model;

/**
 * Created by sunny on 22/3/2015.
 */
public class User {
    String name;
    String phone;
    String email;
    String password;
    boolean emailVerified;

    public User(String name, String phone, String email, String password, boolean emailVerified) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.emailVerified = emailVerified;
    }

    public boolean isEmailVerified() {

        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public User(String name, String number, String email, String password) {
        this.name = name;
        this.phone = number;
        this.email = email;
        this.password = password;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
