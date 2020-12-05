package com.course.entity;

public class BudgetSpending {
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
}
