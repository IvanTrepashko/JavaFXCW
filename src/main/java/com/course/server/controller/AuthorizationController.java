package com.course.server.controller;

import com.course.dao.UserDao;
import com.course.entity.User;
import com.course.model.UserModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AuthorizationController {
    public static void performAction(String action, ObjectInputStream sois, ObjectOutputStream soos)
    {
        switch (action)
        {
            case "SignIn": {
                try {
                    UserModel signIn = (UserModel) sois.readObject();
                    User user = UserDao.select(signIn);

                    if(user != null)
                    {
                        soos.writeObject("SUCCESS");
                        soos.writeObject(user);
                    }
                    else
                    {
                        soos.writeObject("FAILED");
                    }
                    System.out.println(signIn.toString());
                }
                catch (IOException | ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case "SignUp": {
                try {
                    UserModel userModel = (UserModel) sois.readObject();
                    boolean userExists = UserDao.userExists(userModel);

                    if (userExists) {
                        soos.writeObject("FAILED");
                    }
                    else {
                        User user = new User(userModel.getLogin(), userModel.getPassword(),null,userModel.getIsAdmin());
                        if (user.isAdmin())
                        {
                            user.setGroupCode(User.GenerateGroupCode());
                        }
                        UserDao.insert(user);

                        soos.writeObject("SUCCESS");
                    }
                }
                catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}
