package com.course.dao;


import com.course.entity.BudgetPlan;

import java.sql.*;
import java.util.ArrayList;

public class BudgetPlanDao {
    private static String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static String username = "admin";
    private static String password = "admin";

    public static BudgetPlan select(int id)
    {
        ArrayList<BudgetPlan> budgetPlans = new ArrayList<BudgetPlan>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM budgetplan WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(budgetPlans, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return budgetPlans.get(0);
    }

    public static int insert(BudgetPlan budgetPlan) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO budgetplan (groupCode, initialDate, expirationDate, initialMoney, savedMoney, totalMoneySpent) Values (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = SetQueryValues(budgetPlan, conn, sql);
                preparedStatement.execute();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static int update(BudgetPlan budgetPlan) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "UPDATE budgetplan SET  groupCode = ?, initialDate = ?, expirationDate = ?, initialMoney = ?, savedMoney = ?, totalMoneySpent = ? WHERE id = ?";
                PreparedStatement preparedStatement = SetQueryValues(budgetPlan, conn, sql);
                preparedStatement.setInt(7, budgetPlan.getId());
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

                String sql = "DELETE FROM budgetplan WHERE id = ?";
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

    private static PreparedStatement SetQueryValues(BudgetPlan budgetPlan, Connection conn, String sql) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, budgetPlan.getGroupCode());
        preparedStatement.setDate(2, budgetPlan.getInitialDate());
        preparedStatement.setDate(3, budgetPlan.getExpirationDate());
        preparedStatement.setDouble(4, budgetPlan.getInitialMoney());
        preparedStatement.setDouble(5, budgetPlan.getSavedMoney());
        preparedStatement.setDouble(6, budgetPlan.getTotalMoneySpent());

        return  preparedStatement;
    }

    private static void ParseResultSet(ArrayList<BudgetPlan> budgetPlans, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String groupCode = resultSet.getString(2);
            Date initialDate = resultSet.getDate(3);
            Date expirationDate = resultSet.getDate(4);
            double initialMoney = resultSet.getDouble(5);
            double savedMoney = resultSet.getDouble(6);
            double totalMoneySpent = resultSet.getDouble(4);

            BudgetPlan budgetPlan = new BudgetPlan(id, initialDate, expirationDate, initialMoney, savedMoney, totalMoneySpent, groupCode);
            // budgetPlan.setSpendings();

            budgetPlans.add(budgetPlan);
        }
    }
}
