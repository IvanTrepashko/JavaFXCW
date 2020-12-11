package com.course.entity;

import com.course.client.viewmodel.BudgetSpendingViewModel;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class BudgetPlan implements Serializable {
    private int id;
    private ArrayList<BudgetSpendingViewModel> budgetSpendings;
    private Date initialDate;
    private Date expirationDate;
    private String groupCode;
    private double totalSpent;
    private double totalPlanned;
    private double totalSaved;

    public BudgetPlan(int id, ArrayList<BudgetSpendingViewModel> budgetSpendings, Date initialDate, Date expirationDate, String groupCode) {
        this.id = id;
        this.budgetSpendings = budgetSpendings;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.groupCode = groupCode;

        this.countTotalPlanned();
        this.countTotalSpent();
        this.totalSaved = this.totalPlanned - this.totalSpent;
    }

    public BudgetPlan(Date initialDate, Date expirationDate, String groupCode) {
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.groupCode = groupCode;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public double getTotalPlanned() {
        return totalPlanned;
    }

    public double getTotalSaved() {
        return totalSaved;
    }

    public int getId() {
        return id;
    }

    public ArrayList<BudgetSpendingViewModel> getBudgetSpendings() {
        return budgetSpendings;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBudgetSpendings(ArrayList<BudgetSpendingViewModel> budgetSpendings) {
        this.budgetSpendings = budgetSpendings;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    private void countTotalPlanned() {
        for (BudgetSpendingViewModel model : budgetSpendings) {
            this.totalPlanned += model.getPlannedMoney();
        }
    }

    private void countTotalSpent() {
        for (BudgetSpendingViewModel model : budgetSpendings) {
            this.totalSpent += model.getSpentMoney();
        }
    }
}
