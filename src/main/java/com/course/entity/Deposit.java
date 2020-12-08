package com.course.entity;

import java.io.Serializable;
import java.sql.Date;

public class Deposit implements Serializable {
    private int id;
    private double initialMoney;
    private double currentMoney;
    private double interestRate;
    private Date initialDate;
    private Date expirationDate;
    private String groupId;

    public Deposit(double initialMoney, double interestRate, Date initialDate, Date expirationDate, String groupId) {
        this.initialMoney = initialMoney;
        this.interestRate = interestRate;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.groupId = groupId;

        if (this.currentMoney == 0) {
            this.calculateCurrentMoney();
        }
    }

    public Deposit(double initialMoney, double currentMoney, double interestRate, Date initialDate, Date expirationDate, String groupId) {
        this.initialMoney = initialMoney;
        this.currentMoney = currentMoney;
        this.interestRate = interestRate;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.groupId = groupId;
    }

    public Deposit(Deposit that) {
        this.id = that.id;
        this.initialMoney = that.initialMoney;
        this.currentMoney = that.currentMoney;
        this.interestRate = that.interestRate;
        this.initialDate = that.initialDate;
        this.expirationDate = that.expirationDate;
        this.groupId = that.groupId;
    }

    public Deposit(int id, double initialMoney, double currentMoney, double interestRate,
                    Date initialDate, Date expirationDate, String groupId) {
        this.id = id;
        this.initialMoney = initialMoney;
        this.currentMoney = currentMoney;
        this.interestRate = interestRate;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.groupId = groupId;

        if (this.currentMoney == 0) {
           this.calculateCurrentMoney();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInitialMoney(double initialMoney) {
        this.initialMoney = initialMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId(){
        return groupId;
     }

    public int getId() {
        return id;
    }

    public double getInitialMoney() {
        return initialMoney;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    private void calculateCurrentMoney() {
        java.util.Date date = new java.util.Date();
        long difference = date.getTime() - initialDate.getTime();
        long days = difference / 86400000;
        double percent = 1 + (interestRate * 30 / (100 * 365));
        currentMoney = initialMoney * Math.pow(percent, days / 30);
    }
}
