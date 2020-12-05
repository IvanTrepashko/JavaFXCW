package com.course.entity;

import java.sql.Date;

public class Spending {
    private int id;
    private double moneyAmount;
    private Date date;
    private SpendingCategory category;
    private String groupCode;

    public Spending() {

    }

    public Spending(int id, double moneyAmount, Date date, SpendingCategory category, String groupCode) {
        this.moneyAmount = moneyAmount;
        this.date = date;
        this.category = category;
        this.id = id;
        this.groupCode = groupCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public Date getDate() {
        return date;
    }

    public SpendingCategory getCategory() {
        return category;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public static void ChooseCategory()
    {

    }
}
