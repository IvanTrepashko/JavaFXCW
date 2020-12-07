package com.course.client.controller;

import com.course.PageManager;
import com.course.client.ClientConnection;
import com.course.entity.Credit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.sql.Date;
import java.util.ArrayList;

public class MainMenuAdminController {

    @FXML
    public Button saveChangesCreditsButton;
    public Button addNewCreditButton;
    public Button deleteCreditsButton;
    public Button updateCreditsButton;


    // TABS
    @FXML
    private Tab budgetPlanTab;
    @FXML
    private Tab creditsTab;
    @FXML
    private Tab depositsTab;
    @FXML
    private Tab usersTab;
    @FXML
    private Tab infoTab;
    @FXML
    private Tab spendingsTab;

    // TABLES
    @FXML
    private TableView<Credit> creditTable;
//    @FXML
//    private TableView<Credit> creditTable;
//    @FXML
//    private TableView<Credit> creditTable;
//    @FXML
//    private TableView<Credit> creditTable;
//    @FXML
//    private TableView<Credit> creditTable;
//    @FXML
//    private TableView<Credit> creditTable;


    // CREDIT TABLE COLUMNS
    @FXML
    private TableColumn creditId;
    @FXML
    private TableColumn totalMoneyAmount;
    @FXML
    private TableColumn remainingAmount;
    @FXML
    private TableColumn interestRate;
    @FXML
    private TableColumn loanDate;
    @FXML
    private TableColumn repaymentDate;

    private boolean updateBudgetPlan = true;

    @FXML
    private ObservableList<Credit> credits = FXCollections.observableArrayList();

    @FXML
    void initialize()
    {
        loadCredits();

        budgetPlanTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(!updateBudgetPlan) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("LSDASD");
                    alert.showAndWait();
                    updateBudgetPlan = true;
                }
                else
                {
                    updateBudgetPlan = false;
                }
            }
        });
    }

    private void loadCredits() {
        ClientConnection clientConnection = ClientConnection.getInstance();

        totalMoneyAmount.setCellValueFactory(new PropertyValueFactory<Credit, String>("totalMoneyAmount"));
        totalMoneyAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        remainingAmount.setCellValueFactory(new PropertyValueFactory<Credit, String>("remainingAmount"));
        remainingAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        interestRate.setCellValueFactory(new PropertyValueFactory<Credit, String>("interestRate"));
        interestRate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        loanDate.setCellValueFactory(new PropertyValueFactory<Credit, String>("loanDate"));
        loanDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        repaymentDate.setCellValueFactory(new PropertyValueFactory<Credit, String>("repaymentDate"));
        repaymentDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        creditId.setCellValueFactory(new PropertyValueFactory<Credit, String>("id"));

        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("CreditsByGroupCode");
        clientConnection.sendMessage(ClientConnection.getInstance().getCurrentUser().getGroupCode());

        ArrayList<Credit> creditsList = (ArrayList<Credit>)clientConnection.readObject();

        credits.removeAll();
        credits.addAll(creditsList);

        creditTable.setItems(credits);
        creditTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        creditTable.setEditable(true);

        loadCreditsButtons();

    }

    public void editTotalMoneyAmount(TableColumn.CellEditEvent cellEditEvent) {
        double value = (double)cellEditEvent.getNewValue();
        if (doubleIsNegative(value))
        {
            return;
        }
        Credit credit = creditTable.getSelectionModel().getSelectedItem();
        credit.setTotalMoneyAmount(value);
    }

    public void editRemainingMoney(TableColumn.CellEditEvent cellEditEvent) {
        double value = (double)cellEditEvent.getNewValue();

        if (doubleIsNegative(value))
        {
            return;
        }

        Credit credit = creditTable.getSelectionModel().getSelectedItem();
        System.out.println(value);
        credit.setRemainingAmount(value);
    }

    public void editInterestRate(TableColumn.CellEditEvent cellEditEvent) {
        double value = (double)cellEditEvent.getNewValue();

        if ( doubleIsNegative(value) || value > 100)
        {
            return;
        }

        Credit credit = creditTable.getSelectionModel().getSelectedItem();
        credit.setInterestRate(value);

    }

    public void editLoanDate(TableColumn.CellEditEvent cellEditEvent) {
        Credit credit = creditTable.getSelectionModel().getSelectedItem();
        java.util.Date date = (java.util.Date) cellEditEvent.getNewValue();
        java.sql.Date newDate = new Date(date.getTime());
        credit.setLoanDate(newDate);
    }

    public void editRepaymentDate(TableColumn.CellEditEvent cellEditEvent) {
        Credit credit = creditTable.getSelectionModel().getSelectedItem();
        java.util.Date date = (java.util.Date) cellEditEvent.getNewValue();
        java.sql.Date newDate = new Date(date.getTime());
        credit.setRepaymentDate(newDate);
    }

    private boolean doubleIsNegative(double value)
    {
        if (value < 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Введенное значение недопустимо!");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private void loadCreditsButtons() {
        addNewCreditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PageManager.goToPage("add_credit.fxml");
            }
        });

        saveChangesCreditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Save changes credit.");
                ClientConnection connection = ClientConnection.getInstance();
                ObservableList<Credit> credits = FXCollections.observableArrayList(creditTable.getItems());

                ArrayList<Credit> creditsList = new ArrayList<>();

                for(Credit credit : credits) {
                    Credit item = new Credit(credit);
                    creditsList.add(item);
                }

                connection.sendMessage("DataChangerController");
                connection.sendMessage("UpdateCredits");
                connection.sendObject(creditsList);
            }
        });

        deleteCreditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<Credit> allCredits = creditTable.getItems();
                ObservableList<Credit> selectedCredits = creditTable.getSelectionModel().getSelectedItems();
                for (Credit credit:selectedCredits ) {
                    allCredits.remove(credit);
                }

                Credit credit = selectedCredits.get(0);

                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.sendMessage("DeleteDataController");
                clientConnection.sendMessage("DeleteCredit");
                clientConnection.sendObject(credit);
            }
        });

        updateCreditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.sendMessage("DataLoaderController");
                clientConnection.sendMessage("CreditsByGroupCode");
                clientConnection.sendMessage(ClientConnection.getInstance().getCurrentUser().getGroupCode());

                ArrayList<Credit> creditsList = (ArrayList<Credit>)clientConnection.readObject();
                credits.setAll(creditsList);
                creditTable.setItems(credits);
            }
        });
    }
}
