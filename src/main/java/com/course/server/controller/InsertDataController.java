package com.course.server.controller;

import com.course.dao.*;
import com.course.entity.*;
import com.course.model.BudgetModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.LocalDate;

public class InsertDataController {
    public static void performAction(String action, ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            switch (action) {
                case "InsertCredit": {
                    Credit credit = (Credit)sois.readObject();
                    CreditDao.insert(credit);
                    break;
                }
                case "InsertDeposit": {
                    Deposit deposit = (Deposit)sois.readObject();
                    DepositDao.insert(deposit);
                    break;
                }
                case "InsertBudgetPlan": {
                    BudgetModel model = (BudgetModel)sois.readObject();
                    BudgetPlan plan = BudgetPlanDao.select(model.getGroupCode());
                    if (plan != null) {
                        BudgetSpendingDao.delete(plan.getId());
                        BudgetPlanDao.delete(plan.getId());
                    }
                    addNewBudgetPlan(model);
                    break;
                }
                case "InsertSpending": {
                    Spending spending = (Spending)sois.readObject();
                    SpendingDao.insert(spending);
                    BudgetPlan currentBudgetPlan = BudgetPlanDao.select(spending.getGroupCode());
                    if (currentBudgetPlan != null) {
                        if (currentBudgetPlan.getExpirationDate().getTime() - Date.valueOf(LocalDate.now()).getTime() > 0) {
                            BudgetSpending budgetSpending = BudgetSpendingDao.select(currentBudgetPlan.getId(), spending.getCategory().getValue());
                            budgetSpending.setSpentMoney(budgetSpending.getSpentMoney() + spending.getMoneyAmount());
                            BudgetSpendingDao.update(budgetSpending);
                        }
                    }
                }
            }
        }
        catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void addNewBudgetPlan(BudgetModel model) {
        BudgetPlan newPlan = new BudgetPlan(model.getInitialDate(), model.getExpirationDate(), model.getGroupCode());
        BudgetPlanDao.insert(newPlan);
        BudgetPlan plan1 = BudgetPlanDao.select(model.getGroupCode());
        for(BudgetSpending spending : model.getSpendings()) {
            assert plan1 != null;
            spending.setBudgetPlanId(plan1.getId());
            BudgetSpendingDao.insert(spending);
        }
    }
}
