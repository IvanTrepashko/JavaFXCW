package com.course.entity;

import java.io.Serializable;

public class BudgetSpending implements Serializable {
    private int id;
    private SpendingCategory category;
    private double plannedMoney;
    private double spentMoney;
    private int budgetPlanId;

    public BudgetSpending(int id, SpendingCategory category, double plannedMoney, double spentMoney, int budgetPlanId) {
        this.category = category;
        this.id = id;
        this.plannedMoney = plannedMoney;
        this.spentMoney = spentMoney;
        this.budgetPlanId = budgetPlanId;
    }

    public BudgetSpending(SpendingCategory category, double plannedMoney) {
        this.category = category;
        this.plannedMoney = plannedMoney;
        this.spentMoney = 0;
    }

    public int getId() { return id; }

    public int getBudgetPlanId() {
        return budgetPlanId;
    }

    public SpendingCategory getCategory() {
        return category;
    }

    public double getPlannedMoney() {
        return plannedMoney;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(SpendingCategory category) {
        this.category = category;
    }

    public void setPlannedMoney(double plannedMoney) {
        this.plannedMoney = plannedMoney;
    }

    public void setSpentMoney(double spentMoney) {
        this.spentMoney = spentMoney;
    }

    public void setBudgetPlanId(int budgetPlanId) {
        this.budgetPlanId = budgetPlanId;
    }
}
