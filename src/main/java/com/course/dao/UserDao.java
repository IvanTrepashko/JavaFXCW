package com.course.dao;


import com.course.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private static String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static String username = "admin";
    private static String password = "admin";

    public static ArrayList<User> select(int id)
    {
        ArrayList<User> users = new ArrayList<User>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM user WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(users, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return users;
    }


    public static ArrayList<User> select() {

        ArrayList<User> users = new ArrayList<User>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                ParseResultSet(users, resultSet);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return users;
    }

    public static int insert(User user) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "INSERT INTO user (login, password, groupCode, isAdmin) Values (?,?,?,?)";
                PreparedStatement statement = SetQueryValues(user, conn, sql);
                statement.execute();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static int update(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                String sql = "UPDATE user SET login = ?, password = ?, groupCode = ?, isAdmin = ? WHERE id = ?";
                PreparedStatement preparedStatement = SetQueryValues(user, conn, sql);
                preparedStatement.setInt(5, user.getId());
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

                String sql = "DELETE FROM user WHERE id = ?";
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

    private static PreparedStatement SetQueryValues(User user, Connection conn, String sql) throws SQLException {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getGroupCode());
            preparedStatement.setInt(4, user.isAdmin() == true ? 1 : 0);
            return preparedStatement;

    }

    private static void ParseResultSet(ArrayList<User> users, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int userId = resultSet.getInt(1);
            String login = resultSet.getString(2);
            String password = resultSet.getString(3);
            String groupCode = resultSet.getString(4);
            boolean isAdmin = resultSet.getInt(5) == 1 ? true : false;
            User user = new User(userId, login, password, groupCode, isAdmin);
            users.add(user);
        }
    }
}
