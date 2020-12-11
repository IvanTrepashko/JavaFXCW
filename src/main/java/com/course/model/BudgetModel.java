package com.course.model;

import com.course.entity.BudgetSpending;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class BudgetModel implements Serializable {
    private ArrayList<BudgetSpending> spendings;
    private String groupCode;
    private Date initialDate;
    private Date expirationDate;

    public BudgetModel(ArrayList<BudgetSpending> spendings, String groupCode, Date expirationDate) {
        this.spendings = spendings;
        this.groupCode = groupCode;
        this.expirationDate = expirationDate;
        this.initialDate = Date.valueOf(LocalDate.now());
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ArrayList<BudgetSpending> getSpendings() {
        return spendings;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setSpendings(ArrayList<BudgetSpending> spendings) {
        this.spendings = spendings;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
