package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.entity.Credit;
import javafx.collections.ObservableList;

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
