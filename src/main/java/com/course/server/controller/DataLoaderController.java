package com.course.server.controller;

import com.course.client.viewmodel.SpendingViewModel;
import com.course.client.viewmodel.UserViewModel;
import com.course.dao.*;
import com.course.entity.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataLoaderController {

    public static void performAction(String action, ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            String parameter = sois.readObject().toString();

            switch (action)
            {
                case "CreditsByGroupCode":
                {
                    ArrayList<Credit> credits = CreditDao.select(parameter);
                    soos.writeObject(credits);
                    break;
                }
                case "DepositsByGroupCode":
                {
                    ArrayList<Deposit> deposits = DepositDao.select(parameter);
                    soos.writeObject(deposits);
                    break;
                }
                case "UsersByGroupCode":
                {
                    ArrayList<User> users = UserDao.select(parameter);
                    ArrayList<UserViewModel> userViewModels = new ArrayList<>();
                    for(User user : users) {
                        double totalSum = SpendingDao.selectTotalMoney(user.getId());
                        UserViewModel userViewModel = new UserViewModel(user);
                        userViewModel.setTotalSpendings(totalSum);
                        userViewModels.add(userViewModel);
                    }

                    soos.writeObject(userViewModels);
                    break;
                }
                case "SpendingsByUserId":
                {
                    int userId = Integer.parseInt(parameter);
                    ArrayList<Spending> spendings = SpendingDao.select(userId);
                    soos.writeObject(spendings);
                    break;
                }
                case "SpendingsByGroupCode":
                {
                    ArrayList<SpendingViewModel> spendings = SpendingDao.select(parameter);
                    soos.writeObject(spendings);
                    break;
                }
                case "BudgetPlanByGroupCode": {
                    BudgetPlan budgetPlan = BudgetPlanDao.select(parameter);
                    if (budgetPlan != null) {
                        soos.writeObject("SUCCESS");
                        soos.writeObject(budgetPlan);
                    }
                    else {
                        soos.writeObject("ERROR");
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

    }
}
