package com.course.entity;

import java.sql.Date;
import java.util.ArrayList;

public class BudgetPlan {
    private int id;
    private ArrayList<Spending> spendings;
    private Date initialDate;
    private Date expirationDate;
    private double initialMoney;
    private double savedMoney;
    private double totalMoneySpent;
    private String groupCode;

    public BudgetPlan() {
    }

    public BudgetPlan(int id, Date initialDate, Date expirationDate, double initialMoney, double savedMoney, double totalMoneySpent, String groupCode) {
        this.id = id;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.initialMoney = initialMoney;
        this.savedMoney = savedMoney;
        this.totalMoneySpent = totalMoneySpent;
        this.groupCode = groupCode;
    }

    public BudgetPlan(int id, ArrayList<Spending> spendings, Date initialDate, Date expirationDate,
                      double initialMoney, double savedMoney, double totalMoneySpent, String groupCode) {
        this.id = id;
        this.spendings = spendings;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.initialMoney = initialMoney;
        this.savedMoney = savedMoney;
        this.totalMoneySpent = totalMoneySpent;
        this.groupCode = groupCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Spending> getSpendings() {
        return spendings;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public double getInitialMoney() {
        return initialMoney;
    }

    public double getSavedMoney() {
        return savedMoney;
    }

    public double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public void setSpendings(ArrayList<Spending> spendings)
    {
        this.spendings = spendings;
    }
}
