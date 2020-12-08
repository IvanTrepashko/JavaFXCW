package com.course.client.controller;

import com.course.PageManager;
import com.course.client.ClientConnection;
import com.course.client.viewmodel.SpendingViewModel;
import com.course.client.viewmodel.UserViewModel;
import com.course.entity.Credit;
import com.course.entity.Deposit;
import com.course.entity.Spending;
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

    // CREDIT BUTTONS
    @FXML
    public Button saveChangesCreditsButton;
    public Button addNewCreditButton;
    public Button deleteCreditsButton;
    public Button updateCreditsButton;

    // DEPOSIT BUTTONS
    public Button addNewDepositButton;
    public Button saveChangesDepositButton;
    public Button deleteDepositButton;
    public Button updateDepositsButton;

    //USER BUTTONS
    public Button addNewUserButton;
    public TextField groupCode;

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
    @FXML
    private TableView<Deposit> depositTable;
    @FXML
    private TableView<UserViewModel> userTable;
    @FXML
    private TableView<Spending> mySpendingTable;
    @FXML
    private TableView<SpendingViewModel> allSpendingTable;

//    @FXML
//    private TableView<BudgetPlan> budgetPlanTable;

    // ALL SPENDING COLUMNS
    public TableColumn allSpendingMoney;
    public TableColumn allSpendingDate;
    public TableColumn allSpendingCategory;
    public TableColumn allSpendingUserLogin;

    // MY SPENDINGS TABLE COLUMNS
    public TableColumn mySpendingAmount;
    public TableColumn mySpendingDate;
    public TableColumn mySpendingCategory;

    // USER TABLE COLUMNS
    @FXML
    private TableColumn userLogin;
    @FXML
    private TableColumn userTotalSpendings;

    // CREDIT TABLE COLUMNS
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

    // DEPOSIT TABLE COLUMNS
    @FXML
    private TableColumn initialMoney;
    @FXML
    private TableColumn currentMoney;
    @FXML
    private TableColumn depositInterestRate;
    @FXML
    private TableColumn initialDate;
    @FXML
    private TableColumn expirationDate;

    @FXML
    private ObservableList<Credit> credits = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Deposit> deposits = FXCollections.observableArrayList();

    @FXML
    private ObservableList<UserViewModel> users = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Spending> spendings = FXCollections.observableArrayList();

    @FXML
    private ObservableList<SpendingViewModel> allSpendings = FXCollections.observableArrayList();

    @FXML
    void initialize()
    {
        loadCredits();
        loadDeposits();
        loadUsers();
        loadSpendings();
        loadAllSpendings();
    }

    private void loadAllSpendings() {
        ClientConnection clientConnection = ClientConnection.getInstance();

        allSpendingMoney.setCellValueFactory(new PropertyValueFactory<SpendingViewModel, String>("totalMoneyAmount"));

        allSpendingDate.setCellValueFactory(new PropertyValueFactory<SpendingViewModel, String>("date"));

        allSpendingCategory.setCellValueFactory(new PropertyValueFactory<SpendingViewModel, String>("categoryStringModel"));

        allSpendingUserLogin.setCellValueFactory(new PropertyValueFactory<SpendingViewModel, String>("userLogin"));

        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("SpendingsByGroupCode");
        clientConnection.sendObject(clientConnection.getCurrentUser().getGroupCode());

        ArrayList<SpendingViewModel> spendingsList = (ArrayList<SpendingViewModel>)clientConnection.readObject();

        allSpendings.removeAll();
        allSpendings.addAll(spendingsList);
        allSpendingTable.setItems(allSpendings);

        loadAllSpendingsButtons();
    }

    private void loadAllSpendingsButtons() {

    }

    private void loadSpendings() {
        ClientConnection clientConnection = ClientConnection.getInstance();

        mySpendingAmount.setCellValueFactory(new PropertyValueFactory<Spending, String>("moneyAmount"));

        mySpendingDate.setCellValueFactory(new PropertyValueFactory<Spending, String>("date"));

        mySpendingCategory.setCellValueFactory(new PropertyValueFactory<Spending, String>("categoryString"));

        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("SpendingsByUserId");
        clientConnection.sendObject(clientConnection.getCurrentUser().getId());

        ArrayList<Spending> spendingsList = (ArrayList<Spending>)clientConnection.readObject();

        spendings.removeAll();
        spendings.addAll(spendingsList);
        mySpendingTable.setItems(spendings);

        loadMySpendingButtons();
    }

    private void loadMySpendingButtons() {

    }

    private void loadUsers() {
        ClientConnection clientConnection = ClientConnection.getInstance();

        userLogin.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("login"));

        userTotalSpendings.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("totalSpendings"));

        clientConnection.sendMessage("DataLoaderController");
        clientConnection.sendMessage("UsersByGroupCode");
        clientConnection.sendMessage(clientConnection.getCurrentUser().getGroupCode());

        ArrayList<UserViewModel> usersList = (ArrayList<UserViewModel>)clientConnection.readObject();

        users.removeAll();
        users.addAll(usersList);

        userTable.setItems(users);

        groupCode.setText(clientConnection.getCurrentUser().getGroupCode());

        loadUserButtons();
    }

    private void loadUserButtons() {

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
        addNewDepositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PageManager.goToPage("add_deposit.fxml");
            }
        });

        saveChangesDepositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ClientConnection connection = ClientConnection.getInstance();
                ObservableList<Deposit> deposits = FXCollections.observableArrayList(depositTable.getItems());

                ArrayList<Deposit> depositsList = new ArrayList<>();

                for(Deposit deposit : deposits) {
                    Deposit item = new Deposit(deposit);
                    depositsList.add(item);
                }

                connection.sendMessage("DataChangerController");
                connection.sendMessage("UpdateDeposits");
                connection.sendObject(depositsList);
            }
        });

        deleteDepositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<Deposit> allDeposits = depositTable.getItems();
                ObservableList<Deposit> selectedDeposits = depositTable.getSelectionModel().getSelectedItems();

                Deposit depositToDelete = selectedDeposits.get(0);

                allDeposits.remove(depositToDelete);

                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.sendMessage("DeleteDataController");
                clientConnection.sendMessage("DeleteDeposit");
                clientConnection.sendObject(depositToDelete);
            }
        });

        updateDepositsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.sendMessage("DataLoaderController");
                clientConnection.sendMessage("DepositsByGroupCode");
                clientConnection.sendMessage(clientConnection.getCurrentUser().getGroupCode());

                ArrayList<Deposit> depositsList = (ArrayList<Deposit>)clientConnection.readObject();
                deposits.setAll(depositsList);
                depositTable.setItems(deposits);
            }
        });
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

                Credit creditToDelete = selectedCredits.get(0);

                for (Credit credit:selectedCredits ) {
                    allCredits.remove(credit);
                }

                ClientConnection clientConnection = ClientConnection.getInstance();
                clientConnection.sendMessage("DeleteDataController");
                clientConnection.sendMessage("DeleteCredit");
                clientConnection.sendObject(creditToDelete);
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

    public void editInterestRateDeposit(TableColumn.CellEditEvent cellEditEvent) {
        double value = (double)cellEditEvent.getNewValue();
        if (doubleIsNegative(value))
        {
            return;
        }
        Deposit deposit = depositTable.getSelectionModel().getSelectedItem();
        deposit.setInterestRate(value);
    }

    public void editInitialSumDeposit(TableColumn.CellEditEvent cellEditEvent) {
        double value = (double)cellEditEvent.getNewValue();
        if (doubleIsNegative(value))
        {
            return;
        }
        Deposit deposit = depositTable.getSelectionModel().getSelectedItem();
        deposit.setInitialMoney(value);
    }

    public void editCurrentSumDeposit(TableColumn.CellEditEvent cellEditEvent) {
        double value = (double)cellEditEvent.getNewValue();
        if (doubleIsNegative(value))
        {
            return;
        }
        Deposit deposit = depositTable.getSelectionModel().getSelectedItem();
        deposit.setCurrentMoney(value);
    }

    public void editInitialDate(TableColumn.CellEditEvent cellEditEvent) {
        Deposit deposit = depositTable.getSelectionModel().getSelectedItem();
        java.util.Date date = (java.util.Date) cellEditEvent.getNewValue();
        java.sql.Date newDate = new Date(date.getTime());
        deposit.setInitialDate(newDate);
    }

    public void editExpirationDate(TableColumn.CellEditEvent cellEditEvent) {
        Deposit deposit = depositTable.getSelectionModel().getSelectedItem();
        java.util.Date date = (java.util.Date) cellEditEvent.getNewValue();
        java.sql.Date newDate = new Date(date.getTime());
        deposit.setExpirationDate(newDate);
    }
}
