package com.course.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String login;
    private String password;
    private boolean isAdmin;

    public UserModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserModel(String login, String password, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin()
    {
        return this.isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return login + " " + password;
    }
}
