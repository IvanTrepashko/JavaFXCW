package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.dao.DepositDao;
import com.course.entity.Credit;
import com.course.entity.Deposit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
            }
        }
        catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
