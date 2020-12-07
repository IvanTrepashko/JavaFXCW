package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.entity.Credit;

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
