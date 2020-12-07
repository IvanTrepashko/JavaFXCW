package com.course.client.controller;

import com.course.client.ClientConnection;
import com.course.entity.Credit;
import com.course.validation.DoubleValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import javax.xml.validation.Validator;
import java.sql.Date;
import java.time.LocalDate;

public class AddCreditController {

    public TextField totalMoneyAmount;
    public TextField interestRate;
    public TextField remainingAmount;
    public DatePicker loanDate;
    public DatePicker repaymentDate;


    public Button addButton;

    @FXML
    void initialize()
    {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                  String totalMoney = totalMoneyAmount.getText();
                  String remainingMoney = remainingAmount.getText();
                  String interest = interestRate.getText();
                try {
                    ClientConnection clientConnection = ClientConnection.getInstance();

                    double total = Double.parseDouble(totalMoney);
                    double remain = Double.parseDouble(remainingMoney);
                    double inter = Double.parseDouble(interest);
                    LocalDate loan = loanDate.getValue();
                    LocalDate repayment = repaymentDate.getValue();

                    if (inter > 100 || inter < 0)
                    {
                        throw new Exception();
                    }

                    if (loan.compareTo(repayment) > 0)
                    {
                        throw new Exception();
                    }

                    String groupId = clientConnection.getCurrentUser().getGroupCode();

                    Credit credit = new Credit(total, remain, inter, Date.valueOf(loan), Date.valueOf(repayment), groupId);

                    clientConnection.sendMessage("InsertDataController");
                    clientConnection.sendMessage("InsertCredit");
                    clientConnection.sendObject(credit);

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
