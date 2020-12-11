package com.course.client.controller;

import com.course.PageManager;
import com.course.client.ClientConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SetGroupCodeController {
    public TextField groupCodeField;
    public Button confirmButton;

    @FXML
    private void initialize() {
        confirmButton.setOnAction(actionEvent -> {
            String groupCode = groupCodeField.getText();
            ClientConnection client = ClientConnection.getInstance();
            client.sendMessage("DataChangerController");
            client.sendMessage("UpdateUserGroupCode");
            client.sendMessage(groupCode);
            client.sendObject(client.getCurrentUser());
            try {
                if (client.readMessage().equals("ERROR")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setContentText("Такой группы не существует. Пожалуйста, попробуйте еще раз.");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех");
                    alert.setContentText("Группа была успешно добавлена. Пожалуйста, пройдите повторную авторизацию");
                    alert.showAndWait();
                    confirmButton.getScene().getWindow().hide();
                    PageManager.goToPage("authorization.fxml");
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
