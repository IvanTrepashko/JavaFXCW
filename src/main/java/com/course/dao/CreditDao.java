package com.course.dao;

import com.course.entity.Credit;

import java.sql.*;
import java.util.ArrayList;

public class CreditDao {
    private static String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static String username = "admin";
    private static String password = "admin";

    public static ArrayList<Credit> select(int id)
    {
        ArrayList<Credit> records = new ArrayList<Credit>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM credit WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(records, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return records;
    }

    public static ArrayList<Credit> select() {

        ArrayList<Credit> records = new ArrayList<Credit>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM credit");
                ParseResultSet(records, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return records;
    }

    public static int insert(Credit credit) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO credit (totalMoneyAmount, remainingMoney, interestRate, loanDate, repaymentDate, groupCode) Values (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = SetQueryValues(credit, conn, sql);
                preparedStatement.execute();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static int update(Credit credit) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "UPDATE credit SET  totalMoneyAmount = ?, remainingMoney = ?, interestRate = ?, loanDate = ?, repaymentDate = ?, groupCode = ? WHERE id = ?";
                PreparedStatement preparedStatement = SetQueryValues(credit, conn, sql);
                preparedStatement.setInt(7,credit.getId());
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

                String sql = "DELETE FROM credit WHERE id = ?";
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

    private static PreparedStatement SetQueryValues(Credit credit, Connection conn, String sql) throws SQLException {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, credit.getTotalMoneyAmount());
            preparedStatement.setDouble(2, credit.getRemainingAmount());
            preparedStatement.setDouble(3, credit.getInterestRate());
            preparedStatement.setDate(4, credit.getLoanDate());
            preparedStatement.setDate(5, credit.getRepaymentDate());
            preparedStatement.setString(6, credit.getGroupId());

            return  preparedStatement;
    }

    private static void ParseResultSet(ArrayList<Credit> credits, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int creditId = resultSet.getInt(1);
            double totalMoneyAmount = resultSet.getDouble(2);
            double remainingMoney = resultSet.getDouble(3);
            double interestRate = resultSet.getDouble(4);
            Date loanDate = resultSet.getDate(5);
            Date repaymentDate = resultSet.getDate(6);
            String groupCode = resultSet.getString(7);
            String password = resultSet.getString(3);
            Credit credit = new Credit(creditId, totalMoneyAmount, remainingMoney, interestRate, loanDate, repaymentDate, groupCode);
            credits.add(credit);
        }
    }
}
