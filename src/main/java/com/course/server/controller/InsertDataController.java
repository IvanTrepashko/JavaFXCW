package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.entity.Credit;

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
                }
            }
        }
        catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
