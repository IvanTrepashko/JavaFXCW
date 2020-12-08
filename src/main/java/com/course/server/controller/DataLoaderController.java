package com.course.server.controller;

import com.course.client.viewmodel.UserViewModel;
import com.course.dao.CreditDao;
import com.course.dao.DepositDao;
import com.course.dao.SpendingDao;
import com.course.dao.UserDao;
import com.course.entity.Credit;
import com.course.entity.Deposit;
import com.course.entity.Spending;
import com.course.entity.User;

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
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
