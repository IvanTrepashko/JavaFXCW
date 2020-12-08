package com.course.dao;


import com.course.entity.CapitalizationType;
import com.course.entity.Deposit;

import java.sql.*;
import java.util.ArrayList;

public class DepositDao {
    private static String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static String username = "admin";
    private static String password = "admin";

    public static ArrayList<Deposit> select(String groupCode)
    {
        ArrayList<Deposit> deposits = new ArrayList<Deposit>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM deposit WHERE groupCode = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,groupCode);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(deposits, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return deposits;
    }

    public static ArrayList<Deposit> select() {

        ArrayList<Deposit> deposits = new ArrayList<Deposit>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM deposit");
                ParseResultSet(deposits, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return deposits;
    }

    public static int insert(Deposit deposit) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO deposit (initialMoney, currentMoney, interestRate, initialDate, expirationDate, groupCode) Values (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = SetQueryValues(deposit, conn, sql);
                preparedStatement.execute();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static int update(Deposit deposit) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "UPDATE deposit SET  initialMoney = ?, currentMoney = ?, interestRate = ?, initialDate = ?, expirationDate = ?, groupCode = ? WHERE id = ?";
                PreparedStatement preparedStatement = SetQueryValues(deposit, conn, sql);
                preparedStatement.setInt(7,deposit.getId());
                preparedStatement.executeUpdate();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    public static int delete(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "DELETE FROM deposit WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    private static PreparedStatement SetQueryValues(Deposit deposit, Connection conn, String sql) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setDouble(1, deposit.getInitialMoney());
        preparedStatement.setDouble(2, deposit.getCurrentMoney());
        preparedStatement.setDouble(3, deposit.getInterestRate());
        preparedStatement.setDate(4, deposit.getInitialDate());
        preparedStatement.setDate(5, deposit.getExpirationDate());
        preparedStatement.setString(6, deposit.getGroupId());

        return  preparedStatement;
    }

    private static void ParseResultSet(ArrayList<Deposit> deposits, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int depositId = resultSet.getInt(1);
            double initialMoney = resultSet.getDouble(2);
            double currentMoney = resultSet.getDouble(3);
            double interestRate = resultSet.getDouble(4);
            Date initialDate = resultSet.getDate(5);
            Date expirationDate = resultSet.getDate(6);
            String groupCode = resultSet.getString(7);
            Deposit deposit = new Deposit(depositId, initialMoney, currentMoney, interestRate, initialDate, expirationDate, groupCode);
            deposits.add(deposit);
        }
    }
}
