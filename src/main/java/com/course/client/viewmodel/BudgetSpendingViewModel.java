package com.course.client.viewmodel;

import com.course.entity.BudgetSpending;
import com.course.entity.Spending;

import java.io.Serializable;

public class BudgetSpendingViewModel implements Serializable {
    private double plannedMoney;
    private double spentMoney;
    private String categoryString;

    public BudgetSpendingViewModel(BudgetSpending spending) {
        this.plannedMoney = spending.getPlannedMoney();
        this.spentMoney = spending.getSpentMoney();
        this.categoryString = Spending.parseCategoryString(spending.getCategory());
    }

    public BudgetSpendingViewModel(double plannedMoney, double spentMoney, String categoryString) {
        this.plannedMoney = plannedMoney;
        this.spentMoney = spentMoney;
        this.categoryString = categoryString;
    }

    public double getPlannedMoney() {
        return plannedMoney;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setPlannedMoney(double plannedMoney) {
        this.plannedMoney = plannedMoney;
    }

    public void setSpentMoney(double spentMoney) {
        this.spentMoney = spentMoney;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }
}
