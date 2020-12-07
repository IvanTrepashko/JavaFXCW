package com.course.client.controller;

import com.course.PageManager;
import com.course.client.ClientConnection;
import com.course.entity.User;
import com.course.model.UserModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AuthorizationController {

    @FXML
    private TextField passwordField;

    @FXML
    private  TextField loginField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize()
    {
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String pass = passwordField.getText();
                String log = loginField.getText();

                ClientConnection clientConnection = ClientConnection.getInstance();
                UserModel singIn = new UserModel(log,pass);

                clientConnection.sendMessage("AuthorizationController");
                clientConnection.sendMessage("SignIn");
                clientConnection.sendObject(singIn);

                String status = (String)clientConnection.readObject();
                System.out.println(status);
                if (status.equals("FAILED"))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка авторизации");
                    alert.setContentText("Неправильный логин или пароль. Повторите попытку.");
                    alert.showAndWait();
                }
                else {
                    User user = (User) clientConnection.readObject();
                    ClientConnection.getInstance().setCurrentUser(user);
                    passwordField.getScene().getWindow().hide();
                    if (user.isAdmin()) {
                        PageManager.goToPage("main_admin.fxml");
                    }
                    else {
                        PageManager.goToPage("main_user.fxml");
                    }
                }
            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("FKO");
                signUpButton.getScene().getWindow().hide();
                PageManager.goToPage("registration.fxml");
            }
        });

    }
}
