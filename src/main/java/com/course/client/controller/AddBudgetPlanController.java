package com.course.client.controller;

import com.course.client.ClientConnection;
import com.course.entity.BudgetSpending;
import com.course.entity.SpendingCategory;
import com.course.model.BudgetModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.util.ArrayList;

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
        addNewBudgetButton.setOnAction(actionEvent -> {
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

                String groupCode = ClientConnection.getInstance().getCurrentUser().getGroupCode();

                ArrayList<BudgetSpending> newSpendings = new ArrayList<>();
                BudgetSpending foodSpending = new BudgetSpending(SpendingCategory.FOOD, food);
                BudgetSpending hcsSpending = new BudgetSpending(SpendingCategory.HCS, hcs);
                BudgetSpending transportSpending = new BudgetSpending(SpendingCategory.TRANSPORT, transport);
                BudgetSpending entertainmentSpending = new BudgetSpending(SpendingCategory.ENTERTAINMENT, entertainment);
                BudgetSpending creditSpending = new BudgetSpending(SpendingCategory.CREDIT, credit);
                BudgetSpending fuelSpending = new BudgetSpending(SpendingCategory.FUEL, fuel);
                BudgetSpending subsSpending = new BudgetSpending(SpendingCategory.SUBSCRIPTION, subs);
                BudgetSpending clothesSpending = new BudgetSpending(SpendingCategory.CLOTHES, clothes);
                BudgetSpending otherSpending = new BudgetSpending(SpendingCategory.OTHER, other);

                newSpendings.add(foodSpending);
                newSpendings.add(hcsSpending);
                newSpendings.add(transportSpending);
                newSpendings.add(entertainmentSpending);
                newSpendings.add(creditSpending);
                newSpendings.add(fuelSpending);
                newSpendings.add(subsSpending);
                newSpendings.add(clothesSpending);
                newSpendings.add(otherSpending);

                BudgetModel budgetModel = new BudgetModel(newSpendings, groupCode, expiration);

                ClientConnection.getInstance().sendMessage("InsertDataController");
                ClientConnection.getInstance().sendMessage("InsertBudgetPlan");
                ClientConnection.getInstance().sendObject(budgetModel);

                addNewBudgetButton.getScene().getWindow().hide();
            }
            catch (NumberFormatException ex) {
                showAlert();
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
