package com.course.client.controller;

import com.course.client.ClientConnection;
import com.course.entity.Spending;
import com.course.entity.SpendingCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class AddSpendingController {

    public ComboBox<String> categoryCombobox;
    public TextField moneyAmount;
    public Button addNewSpendingButton;

    @FXML
    private final ObservableList<String> spendingCategories = FXCollections.observableArrayList("Еда", "ЖКХ", "Транспорт", "Развлечения", "Кредит", "Бензин", "Подписки", "Одежда", "Другое");

    @FXML
    private void initialize() {
        categoryCombobox.setItems(spendingCategories);
        categoryCombobox.setValue("Еда");

        addNewSpendingButton.setOnAction(actionEvent -> {
            try {
                ClientConnection client = ClientConnection.getInstance();

                double money = Double.parseDouble(moneyAmount.getText());
                String category = categoryCombobox.getValue();
                SpendingCategory spendingCategory = parseStringToSpendingCategory(category);
                Date date = Date.valueOf(LocalDate.now());
                String groupCode = client.getCurrentUser().getGroupCode();
                int userId = client.getCurrentUser().getId();

                Spending spending = new Spending(money,date,spendingCategory,groupCode,userId);

                client.sendMessage("InsertDataController");
                client.sendMessage("InsertSpending");
                client.sendObject(spending);

                addNewSpendingButton.getScene().getWindow().hide();
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

    private SpendingCategory parseStringToSpendingCategory(String category) {
        switch (category) {
            case "Еда":
                return SpendingCategory.FOOD;
            case "ЖКХ":
                return SpendingCategory.HCS;
            case "Транспорт":
                return SpendingCategory.TRANSPORT;
            case "Развлечения":
                return SpendingCategory.ENTERTAINMENT;
            case "Кредит":
                return SpendingCategory.CREDIT;
            case "Бензин":
                return SpendingCategory.FUEL;
            case "Подписки":
                return SpendingCategory.SUBSCRIPTION;
            case "Одежда":
                return SpendingCategory.CLOTHES;
            case "Другое":
                return SpendingCategory.OTHER;
        }
        return null;
    }
}
