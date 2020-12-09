package com.course.dao;


import com.course.client.viewmodel.BudgetSpendingViewModel;
import com.course.entity.BudgetPlan;
import com.course.entity.BudgetSpending;

import java.sql.*;
import java.util.ArrayList;

public class BudgetPlanDao {
    private static String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static String username = "admin";
    private static String password = "admin";

    public static BudgetPlan select(String groupCode) {
        ArrayList<BudgetPlan> budgetPlans = new ArrayList<BudgetPlan>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM budgetplan WHERE groupCode = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, groupCode);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(budgetPlans, resultSet);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (budgetPlans.size() != 0) {
            return budgetPlans.get(0);
        }
        else {
            return null;
        }
    }

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

                String sql = "INSERT INTO budgetplan (groupCode, initialDate, expirationDate) Values (?,?,?)";
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

                String sql = "UPDATE budgetplan SET  groupCode = ?, initialDate = ?, expirationDate = ? WHERE id = ?";
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
        return  preparedStatement;
    }

    private static void ParseResultSet(ArrayList<BudgetPlan> budgetPlans, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String groupCode = resultSet.getString(2);
            Date initialDate = resultSet.getDate(3);
            Date expirationDate = resultSet.getDate(4);
            ArrayList<BudgetSpending> budgetSpendings = BudgetSpendingDao.select(id);
            ArrayList<BudgetSpendingViewModel> models = new ArrayList<>();
            for(BudgetSpending spending : budgetSpendings)
            {
                BudgetSpendingViewModel model = new BudgetSpendingViewModel(spending);
                models.add(model);
            }
            BudgetPlan budgetPlan = new BudgetPlan(id, models, initialDate, expirationDate, groupCode);
            budgetPlans.add(budgetPlan);
        }
    }
}
