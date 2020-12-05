package com.course.dao;

import com.course.entity.BudgetSpending;
import com.course.entity.SpendingCategory;

import java.sql.*;
import java.util.ArrayList;

public class BudgetSpendingDao {
    private static String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static String username = "admin";
    private static String password = "admin";

//    public static ArrayList<BudgetSpending> select(int id)
//    {
//        ArrayList<BudgetSpending> budgetSpendings = new ArrayList<BudgetSpending>();
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = DriverManager.getConnection(url, username, password)){
//                String sql = "SELECT * FROM spending WHERE id = ?";
//                PreparedStatement preparedStatement = conn.prepareStatement(sql);
//                preparedStatement.setInt(1,id);
//                ResultSet resultSet = preparedStatement.executeQuery();
//                ParseResultSet(budgetSpendings, resultSet);
//            }
//        }
//        catch(Exception ex){
//            System.out.println(ex);
//        }
//
//        return budgetSpendings;
//    }

    public static ArrayList<BudgetSpending> select() {

        ArrayList<BudgetSpending> budgetSpendings = new ArrayList<BudgetSpending>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM budgetspending");
                ParseResultSet(budgetSpendings, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return budgetSpendings;
    }

    public static int insert(BudgetSpending budgetSpending) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO budgetspending (category, spentMoney, plannedMoney, budgetPlanId) Values (?,?,?,?)";
                PreparedStatement preparedStatement = SetQueryValues(budgetSpending, conn, sql);
                preparedStatement.execute();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static int update(BudgetSpending budgetSpending) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "UPDATE budgetspending SET  category = ?, spentMoney = ?, plannedMoney = ?, budgetPlanId = ? WHERE id = ?";
                PreparedStatement preparedStatement = SetQueryValues(budgetSpending, conn, sql);
                preparedStatement.setInt(5,budgetSpending.getId());
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

                String sql = "DELETE FROM budgetspending WHERE id = ?";
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

    private static PreparedStatement SetQueryValues(BudgetSpending budgetSpending, Connection conn, String sql) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, budgetSpending.getCategory().ordinal());
        preparedStatement.setDouble(2, budgetSpending.getSpentMoney());
        preparedStatement.setDouble(3, budgetSpending.getPlannedMoney());
        preparedStatement.setInt(4, budgetSpending.getBudgetPlanId());

        return  preparedStatement;
    }

    private static void ParseResultSet(ArrayList<BudgetSpending> budgetSpendings, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            SpendingCategory spendingCategory = SpendingCategory.valueOf(resultSet.getInt(2));
            double spentMoney = resultSet.getDouble(3);
            double plannedMoney = resultSet.getDouble(4);
            int budgetPlanId = resultSet.getInt(5);

            BudgetSpending budgetSpending = new BudgetSpending(id, spendingCategory, plannedMoney, spentMoney, budgetPlanId );
            budgetSpendings.add(budgetSpending);
        }
    }
}
