package com.course.entity;

import java.sql.Date;

public class Credit {
    private int id;
    private double totalMoneyAmount;
    private double remainingAmount;
    private double interestRate;
    private Date loanDate;
    private Date repaymentDate;
    private String groupId;

    public Credit(int id, double totalMoneyAmount, double remainingAmount, double interestRate,
                  Date loanDate, Date repaymentDate, String groupId) {
        this.id = id;
        this.totalMoneyAmount = totalMoneyAmount;
        this.remainingAmount = remainingAmount;
        this.interestRate = interestRate;
        this.loanDate = loanDate;
        this.repaymentDate = repaymentDate;
        this.groupId = groupId;
    }

    public Credit(double totalMoneyAmount, double remainingAmount, double interestRate, Date loanDate, Date repaymentDate, String groupId) {
        this.totalMoneyAmount = totalMoneyAmount;
        this.remainingAmount = remainingAmount;
        this.interestRate = interestRate;
        this.loanDate = loanDate;
        this.repaymentDate = repaymentDate;
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getId() {
        return id;
    }

    public double getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setTotalMoneyAmount(double totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
