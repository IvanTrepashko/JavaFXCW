package com.course.client.controller;

import com.course.PageManager;
import com.course.client.ClientConnection;
import com.course.client.viewmodel.BudgetSpendingViewModel;
import com.course.entity.BudgetPlan;
import com.course.entity.Credit;
import com.course.entity.Deposit;
import com.course.entity.Spending;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;

public class MainMenuUserController {

    public TableColumn budgetPlanned;
    public TextField totalSpent;
    public TextField totalSaved;
    public TableView budgetPlanTable;
    public TableColumn budgetCategory;
    public TableColumn budgetSpent;
    public DatePicker budgetInitial;
    public DatePicker budgetExpiration;
    public Button updateBudgetButton;

    public TableView creditTable;
    public TableColumn totalMoneyAmount;
    public TableColumn remainingAmount;
    public TableColumn interestRate;
    public TableColumn loanDate;
    public TableColumn repaymentDate;
    public Button updateCreditsButton;

    public TableView depositTable;
    public TableColumn initialMoney;
    public TableColumn currentMoney;
    public TableColumn depositInterestRate;
    public TableColumn initialDate;
    public TableColumn expirationDate;
    public Button updateDepositsButton;

    public TableView mySpendingTable;
    public TableColumn mySpendingAmount;
    public TableColumn mySpendingDate;
    public TableColumn mySpendingCategory;
    public Button addNewSpendingButton;
    public Button updateMySpendingsButton;

    @FXML
    private ObservableList<BudgetSpendingViewModel> budgetSpendings = FXCollections.observableArrayList();

    @FXML
    private final ObservableList<Credit> credits = FXCollections.observableArrayList();

    @FXML
    private final ObservableList<Deposit> deposits = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Spending> spendings = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        loadBudgetPlan();
        loadCredits();
        loadDeposits();
        loadSpendings();
    }

    private void loadSpendings() {
        mySpendingAmount.setCellValueFactory(new PropertyValueFactory<Spending, String>("moneyAmount"));

        mySpendingDate.setCellValueFactory(new PropertyValueFactory<Spending, String>("date"));

        mySpendingCategory.setCellValueFactory(new PropertyValueFactory<Spending, String>("categoryString"));

        updateMySpendings();

        loadMySpendingButtons();
    }

    private void loadMySpendingButtons() {
        updateMySpendingsButton.setOnAction(actionEvent -> updateMySpendings());

        addNewSpendingButton.setOnAction(actionEvent -> PageManager.goToPage("add_spending.fxml"));
    }

    private void updateMySpendings() {
        ClientConnection clientConnection = ClientConnection.getInstance();
        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("SpendingsByUserId");
        clientConnection.sendObject(clientConnection.getCurrentUser().getId());

        ArrayList<Spending> spendingsList = (ArrayList<Spending>)clientConnection.readObject();

        spendings = FXCollections.observableArrayList();
        spendings.addAll(spendingsList);
        mySpendingTable.setItems(spendings);
    }

    private void loadDeposits() {
        ClientConnection clientConnection = ClientConnection.getInstance();

        initialMoney.setCellValueFactory(new PropertyValueFactory<Deposit, String>("initialMoney"));
        initialMoney.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        currentMoney.setCellValueFactory(new PropertyValueFactory<Deposit, String>("currentMoney"));
        currentMoney.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        depositInterestRate.setCellValueFactory(new PropertyValueFactory<Deposit, String>("interestRate"));
        depositInterestRate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        initialDate.setCellValueFactory(new PropertyValueFactory<Deposit, String>("initialDate"));
        initialDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        expirationDate.setCellValueFactory(new PropertyValueFactory<Deposit, String>("expirationDate"));
        expirationDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("DepositsByGroupCode");
        clientConnection.sendMessage(ClientConnection.getInstance().getCurrentUser().getGroupCode());

        ArrayList<Deposit> depositsList = (ArrayList<Deposit>)clientConnection.readObject();

        deposits.removeAll();
        deposits.addAll(depositsList);

        depositTable.setItems(deposits);
        depositTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        depositTable.setEditable(true);

        loadDepositButtons();
    }

    private void loadDepositButtons() {
        updateDepositsButton.setOnAction(actionEvent -> {
            ClientConnection clientConnection = ClientConnection.getInstance();
            clientConnection.sendMessage("DataLoaderController");
            clientConnection.sendMessage("DepositsByGroupCode");
            clientConnection.sendMessage(clientConnection.getCurrentUser().getGroupCode());

            ArrayList<Deposit> depositsList = (ArrayList<Deposit>)clientConnection.readObject();
            deposits.setAll(depositsList);
            depositTable.setItems(deposits);
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

    private void loadCreditsButtons() {
        updateCreditsButton.setOnAction(actionEvent -> {
            ClientConnection clientConnection = ClientConnection.getInstance();
            clientConnection.sendMessage("DataLoaderController");
            clientConnection.sendMessage("CreditsByGroupCode");
            clientConnection.sendMessage(ClientConnection.getInstance().getCurrentUser().getGroupCode());

            ArrayList<Credit> creditsList = (ArrayList<Credit>)clientConnection.readObject();
            credits.setAll(creditsList);
            creditTable.setItems(credits);
        });
    }

    private void loadBudgetPlan() {
        budgetCategory.setCellValueFactory(new PropertyValueFactory<BudgetSpendingViewModel, String>("categoryString"));

        budgetPlanned.setCellValueFactory(new PropertyValueFactory<BudgetSpendingViewModel, String>("plannedMoney"));

        budgetSpent.setCellValueFactory(new PropertyValueFactory<BudgetSpendingViewModel, String>("spentMoney"));

        updateBudgetPlan();
        loadBudgetPlanButtons();
    }

    private void loadBudgetPlanButtons() {
        updateBudgetButton.setOnAction(actionEvent -> updateBudgetPlan());
    }

    private void updateBudgetPlan() {
        ClientConnection clientConnection = ClientConnection.getInstance();
        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("BudgetPlanByGroupCode");
        clientConnection.sendObject(clientConnection.getCurrentUser().getGroupCode());

        String status = (String) clientConnection.readObject();
        if (status.equals("SUCCESS")) {
            BudgetPlan budgetPlan = (BudgetPlan) clientConnection.readObject();
            ArrayList<BudgetSpendingViewModel> budgetSpending = budgetPlan.getBudgetSpendings();
            budgetSpendings = FXCollections.observableArrayList();
            budgetSpendings.addAll(budgetSpending);
            budgetPlanTable.setItems(budgetSpendings);

            totalSaved.setText(Double.toString(budgetPlan.getTotalSaved()));
            totalSpent.setText(Double.toString(budgetPlan.getTotalSpent()));
            budgetInitial.setValue(budgetPlan.getInitialDate().toLocalDate());
            budgetExpiration.setValue(budgetPlan.getExpirationDate().toLocalDate());
        }
    }
}
