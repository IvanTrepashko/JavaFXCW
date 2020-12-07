package com.course.server.controller;

import com.course.dao.CreditDao;
import com.course.entity.Credit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataLoaderController {

    public static void performAction(String action, ObjectInputStream sois, ObjectOutputStream soos) {
        try {
            String groupCode = sois.readObject().toString();

            switch (action)
            {
                case "CreditsByGroupCode":
                {
                    ArrayList<Credit> credits = CreditDao.select(groupCode);
                    soos.writeObject(credits);
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
