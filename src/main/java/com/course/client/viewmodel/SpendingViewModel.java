package com.course.client.viewmodel;

import java.io.Serializable;
import java.sql.Date;

public class SpendingViewModel implements Serializable {
    private double totalMoneyAmount;
    private Date date;
    private String categoryStringModel;
    private String userLogin;

    public SpendingViewModel(double totalMoneyAmount, Date date, String categoryStringModel, String userLogin) {
        this.totalMoneyAmount = totalMoneyAmount;
        this.date = date;
        this.categoryStringModel = categoryStringModel;
        this.userLogin = userLogin;
    }

    public double getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategoryStringModel() {
        return categoryStringModel;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setTotalMoneyAmount(double totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSpendingCategory(String categoryString) {
        this.categoryStringModel = categoryString;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
