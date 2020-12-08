package com.course.client.viewmodel;

import com.course.entity.User;

public class UserViewModel {
    private int id;
    private String login;
    private double totalSpendings;

    public UserViewModel(int id, String login, double totalSpendings) {
        this.id = id;
        this.login = login;
        this.totalSpendings = totalSpendings;
    }

    public UserViewModel(User user){
        this.id = user.getId();
        this.login = user.getLogin();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setTotalSpendings(double totalSpendings) {
        this.totalSpendings = totalSpendings;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public double getTotalSpendings() {
        return totalSpendings;
    }
}
