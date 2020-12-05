package com.course.entity;

import java.sql.Date;

public class Deposit {
    private int id;
    private double initialMoney;
    private double currentMoney;
    private double interestRate;
    private CapitalizationType capitalizationType;
    private Date initialDate;
    private Date expirationDate;
    private String groupId;

    public Deposit(double initialMoney, double currentMoney, double interestRate, CapitalizationType capitalizationType, Date initialDate, Date expirationDate, String groupId) {
        this.initialMoney = initialMoney;
        this.currentMoney = currentMoney;
        this.interestRate = interestRate;
        this.capitalizationType = capitalizationType;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.groupId = groupId;
    }

    public Deposit(int id, double initialMoney, double currentMoney, double interestRate,
                    CapitalizationType capitalizationType, Date initialDate, Date expirationDate, String groupId) {
        this.id = id;
        this.initialMoney = initialMoney;
        this.currentMoney = currentMoney;
        this.interestRate = interestRate;
        this.initialDate = initialDate;
        this.expirationDate = expirationDate;
        this.capitalizationType = capitalizationType;
        this.groupId = groupId;
    }

     public String getGroupId(){
        return groupId;
     }

    public int getId() {
        return id;
    }

    public CapitalizationType getCapitalizationType(){
        return capitalizationType;
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
        long difference = initialDate.getTime() - date.getTime();
        long days = difference % 86400000;
        switch (capitalizationType)
        {
            case NO:
            {
                currentMoney = 1 + (initialMoney * interestRate *  days / (365 * 100));
                break;
            }
            case MONTHLY:
            {
                double percent = 1 + (interestRate * 30 / (100 * 365));
                currentMoney = initialMoney * Math.pow(percent, days / 30);
                break;
            }
            case YEARLY:
            {
                double percent = 1 * (interestRate * 365 / (100 * 365));
                currentMoney = initialMoney * Math.pow(percent, days / 365);
                break;
            }
        }
    }
}
