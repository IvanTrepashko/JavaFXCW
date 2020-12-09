package com.course.client.controller;

import com.course.entity.BudgetSpending;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;

public class AddBudgetPlanController {
    public TextField foodField;
    public TextField hcsField;
    public TextField transportField;
    public TextField entertainmentField;
    public TextField creditField;
    public TextField fuelField;
    public TextField subsField;
    public TextField clothesField;
    public TextField otherField;
    public Button addNewBudgetButton;
    public DatePicker expirationField;

    @FXML
    private void initialize() {
        addNewBudgetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    double food = Double.parseDouble(foodField.getText());
                    double hcs = Double.parseDouble(hcsField.getText());
                    double transport = Double.parseDouble(transportField.getText());
                    double entertainment = Double.parseDouble(entertainmentField.getText());
                    double credit = Double.parseDouble(creditField.getText());
                    double fuel = Double.parseDouble(fuelField.getText());
                    double subs = Double.parseDouble(subsField.getText());
                    double clothes = Double.parseDouble(clothesField.getText());
                    double other = Double.parseDouble(otherField.getText());
                    Date expiration = Date.valueOf(expirationField.getValue());
                    java.util.Date date = new java.util.Date();
                    if (expiration.getTime() < date.getTime())
                    {
                        showAlert();
                    }

                }
                catch (NumberFormatException ex) {
                    showAlert();
                }
            }
        });
    }
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода.");
        alert.setContentText("Ошибка ввода. Проверьте введенные данные.");
        alert.showAndWait();
    }
}
