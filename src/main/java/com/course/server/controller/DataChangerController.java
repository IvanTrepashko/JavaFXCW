package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.dao.DepositDao;
import com.course.dao.UserDao;
import com.course.entity.Credit;
import com.course.entity.Deposit;
import com.course.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataChangerController {

    public static void performAction(String action, ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            switch (action) {
                case "UpdateCredits":
                {
                    System.out.println("Update credits.");
                    ArrayList<Credit> credits = (ArrayList<Credit>) sois.readObject();
                    for(Credit credit : credits) {
                        CreditDao.update(credit);
                    }
                    break;
                }
                case "UpdateDeposits":
                {
                    ArrayList<Deposit> deposits = (ArrayList<Deposit>) sois.readObject();
                    for(Deposit deposit : deposits) {
                        DepositDao.update(deposit);
                    }
                    break;
                }
                case "UpdateUserGroupCode":
                {
                    String groupCode = (String)sois.readObject();
                    User currentUser = (User)sois.readObject();

                    User user = UserDao.selectAdmin(groupCode);
                    if (user != null) {
                        currentUser.setGroupCode(groupCode);
                        UserDao.update(currentUser);
                        soos.writeObject("SUCCESS");
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
