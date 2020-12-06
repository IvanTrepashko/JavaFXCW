package com.course.client.controller;

import com.course.PageManager;
import com.course.client.ClientConnection;
import com.course.model.UserModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField passwordRepeatField;

    @FXML
    private CheckBox isAdminField;

    @FXML
    private Button registerButton;

    @FXML
    private Button goBackButton;

    @FXML
    void initialize()
    {
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String login = loginField.getText();
                String password = passwordField.getText();
                String passwordRepeat = passwordRepeatField.getText();
                boolean isAdmin = isAdminField.isSelected();

                if (!password.equals(passwordRepeat))
                {
                    Alert invalidPasswordAlert = new Alert(Alert.AlertType.ERROR);
                    invalidPasswordAlert.setTitle("Ошибка!");
                    invalidPasswordAlert.setContentText("Пароли не совпадают! Повторите попытку!");
                    invalidPasswordAlert.showAndWait();
                }
                else
                {
                    UserModel user = new UserModel(login, password, isAdmin);

                    ClientConnection clientConnection = ClientConnection.getInstance();

                    clientConnection.sendMessage("AuthorizationController");
                    clientConnection.sendMessage("SignUp");
                    clientConnection.sendObject(user);

                    try {
                        String status = clientConnection.readMessage();
                        if (status.equals("SUCCESS"))
                        {
                            registerButton.getScene().getWindow().hide();
                            Alert registrationSuccess = new Alert(Alert.AlertType.INFORMATION);
                            registrationSuccess.setTitle("Регистрация");
                            registrationSuccess.setContentText("Регистрация прошла успешно!");
                            registrationSuccess.showAndWait();
                            PageManager.goToPage("authorization.fxml");
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка!");
                            alert.setContentText("Ошибка во время регистрации! Такой логин уже существует. Повторите позже.");
                            alert.showAndWait();
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        goBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                goBackButton.getScene().getWindow().hide();
                PageManager.goToPage("authorization.fxml");
            }
        });
    }
}
