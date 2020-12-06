package com.course.entity;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Random;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String groupCode;
    private boolean isAdmin;

    public static String GenerateGroupCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, String groupCode, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.groupCode = groupCode;
        this.isAdmin = isAdmin;
    }

    public User(String login, String password, String groupCode, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.groupCode = groupCode;
        this.isAdmin = isAdmin;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
