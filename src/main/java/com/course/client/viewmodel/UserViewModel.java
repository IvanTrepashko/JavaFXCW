package com.course.client.viewmodel;

public class UserViewModel {
    private int id;
    private String login;
    private double totalSpendings;

    public UserViewModel(int id, String login, double totalSpendings) {
        this.id = id;
        this.login = login;
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
