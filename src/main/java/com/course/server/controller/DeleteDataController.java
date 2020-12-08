package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.dao.DepositDao;
import com.course.entity.Credit;
import com.course.entity.Deposit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteDataController {
    public static void performAction(String action, ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            switch (action) {
                case "DeleteCredit": {
                    Credit credit = (Credit)sois.readObject();
                    CreditDao.delete(credit.getId());
                    break;
                }
                case "DeleteDeposit": {
                    Deposit deposit = (Deposit)sois.readObject();
                    DepositDao.delete(deposit.getId());
                    break;
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
