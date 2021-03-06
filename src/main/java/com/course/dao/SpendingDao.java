package com.course.dao;


import com.course.client.viewmodel.SpendingViewModel;
import com.course.entity.Spending;
import com.course.entity.SpendingCategory;

import java.sql.*;
import java.util.ArrayList;

public class SpendingDao {
    private static final String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static final String username = "admin";
    private static final String password = "admin";

    public static ArrayList<SpendingViewModel> select(String groupCode)
    {
        ArrayList<SpendingViewModel> spendigs = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT sp.moneyAmount, sp.date, sp.category, us.login FROM spending sp, user us WHERE sp.groupCode = ? AND sp.user_id = us.id";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, groupCode);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    double money = resultSet.getDouble(1);
                    Date date = resultSet.getDate(2);
                    int category = resultSet.getInt(3);
                    SpendingCategory cat = SpendingCategory.values()[category - 1];
                    String stringCategory = Spending.parseCategoryString(cat);
                    String login = resultSet.getString(4);

                    SpendingViewModel spending = new SpendingViewModel(money, date, stringCategory, login);
                    spendigs.add(spending);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return spendigs;
    }

    public static ArrayList<Spending> select(int user_id)
    {
        ArrayList<Spending> spendings = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM spending WHERE user_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, user_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(spendings, resultSet);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return spendings;
    }

    public static double selectTotalMoney(int userId){
        double result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT SUM(moneyAmount) FROM spending WHERE user_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result = resultSet.getDouble(1);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Spending> select() {

        ArrayList<Spending> spendings = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM spending");
                ParseResultSet(spendings, resultSet);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return spendings;
    }

    public static int insert(Spending spending) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO spending (moneyAmount, date, category, groupCode, user_id) Values (?,?,?,?,?)";
                PreparedStatement preparedStatement = SetQueryValues(spending, conn, sql);
                preparedStatement.execute();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }

    public static int update(Spending spending) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "UPDATE spending SET  moneyAmount = ?, date = ?, category = ?, groupCode = ?, user_id = ? WHERE id = ?";
                PreparedStatement preparedStatement = SetQueryValues(spending, conn, sql);
                preparedStatement.setInt(6,spending.getId());
                preparedStatement.executeUpdate();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public static int delete(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "DELETE FROM spending WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }

    private static PreparedStatement SetQueryValues(Spending spending, Connection conn, String sql) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setDouble(1, spending.getMoneyAmount());
        preparedStatement.setDate(2, spending.getDate());
        preparedStatement.setInt(3, spending.getCategory().getValue());
        preparedStatement.setString(4, spending.getGroupCode());
        preparedStatement.setInt(5, spending.getUserId());

        return  preparedStatement;
    }

    private static void ParseResultSet(ArrayList<Spending> spendings, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            double moneyAmount = resultSet.getDouble(2);
            Date date = resultSet.getDate(3);
            SpendingCategory spendingCategory = SpendingCategory.valueOf(resultSet.getInt(4));
            String groupCode = resultSet.getString(5);
            int userId = resultSet.getInt(6);
            Spending spending = new Spending(id, moneyAmount, date, spendingCategory, groupCode, userId);

            spendings.add(spending);
        }
    }
}
