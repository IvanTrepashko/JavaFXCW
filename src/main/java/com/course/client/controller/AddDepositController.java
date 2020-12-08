package com.course.client.controller;

import com.course.client.ClientConnection;
import com.course.entity.Credit;
import com.course.entity.Deposit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class AddDepositController {
    public TextField initialMoney;
    public TextField interestRate;
    public DatePicker initialDate;
    public DatePicker expirationDate;
    public Button addButton;

    @FXML
    void initialize()
    {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String initial = initialMoney.getText();
                String interest = interestRate.getText();
                try {
                    ClientConnection clientConnection = ClientConnection.getInstance();

                    double init = Double.parseDouble(initial);
                    double inter = Double.parseDouble(interest);
                    LocalDate loan = initialDate.getValue();
                    LocalDate repayment = expirationDate.getValue();

                    if (inter > 100 || inter < 0)
                    {
                        throw new Exception();
                    }

                    if (loan.compareTo(repayment) > 0)
                    {
                        throw new Exception();
                    }

                    String groupId = clientConnection.getCurrentUser().getGroupCode();

                    Deposit deposit = new Deposit(init, inter, Date.valueOf(loan), Date.valueOf(repayment), groupId);

                    clientConnection.sendMessage("InsertDataController");
                    clientConnection.sendMessage("InsertDeposit");
                    clientConnection.sendObject(deposit);

                    addButton.getScene().getWindow().hide();
                }
                catch (Exception ex)
                {
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
