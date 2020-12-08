package com.course.entity;

import java.io.Serializable;
import java.sql.Date;

public class Spending implements Serializable {
    private int id;
    private double moneyAmount;
    private Date date;
    private SpendingCategory category;
    private String groupCode;
    private int userId;
    private String categoryString;

    public Spending() {

    }

    public Spending(int id, double moneyAmount, Date date, SpendingCategory category, String groupCode, int userId) {
        this.moneyAmount = moneyAmount;
        this.date = date;
        this.category = category;
        this.id = id;
        this.groupCode = groupCode;
        this.userId = userId;
        this.categoryString = Spending.parseCategoryString(this.category);
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(SpendingCategory category) {
        this.category = category;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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

    public static String parseCategoryString(SpendingCategory category) {
        switch (category) {
            case FOOD:
            {
                return "Еда";
            }
            case HCS:
            {
                return "ЖКХ";
            }
            case FUEL:
            {
                return "Топливо";
            }
            case CREDIT:
            {
                return "Кредит";
            }
            case CLOTHES:
            {
                return "Одежда";
            }
            case TRANSPORT: {
                return "Транспорт";
            }
            case SUBSCRIPTION: {
                return "Подписки";
            }
            case ENTERTAINMENT:
            {
                return "Развлечения";
            }
            case OTHER:
            {
                return "Другое";
            }
            default: {
                return null;
            }
        }
    }
}
