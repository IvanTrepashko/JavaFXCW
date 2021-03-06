package com.course.dao;


import com.course.entity.User;
import com.course.model.UserModel;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private static final String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=UTC";
    private static final String username = "admin";
    private static final String password = "admin";

    public static User select(UserModel model) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM user WHERE login = ? AND password = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, model.getLogin());
                preparedStatement.setString(2, model.getPassword());
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(users, resultSet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (users.size() != 0) {
            return users.get(0);
        }
        else {
            return null;
        }
    }

    public static User selectAdmin(String groupCode) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM user WHERE groupCode = ? AND isAdmin = 1";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, groupCode);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(users, resultSet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (users.size() != 0) {
            return users.get(0);
        }
        else {
            return null;
        }
    }

    public static ArrayList<User> select(String groupCode) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM user WHERE groupCode = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, groupCode);
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(users, resultSet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public static boolean userExists(UserModel user)
    {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM user WHERE login = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, user.getLogin());
                ResultSet resultSet = preparedStatement.executeQuery();
                ParseResultSet(users, resultSet);
            }

            return users.size() != 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static ArrayList<User> select() {

        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                ParseResultSet(users, resultSet);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
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
            ex.printStackTrace();
        }

        return 0;
    }

    public static void update(User user) {
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
            ex.printStackTrace();
        }
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
            ex.printStackTrace();
        }

        return 0;
    }

    private static PreparedStatement SetQueryValues(User user, Connection conn, String sql) throws SQLException {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getGroupCode());
            preparedStatement.setInt(4, user.isAdmin() ? 1 : 0);
            return preparedStatement;

    }

    private static void ParseResultSet(ArrayList<User> users, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int userId = resultSet.getInt(1);
            String login = resultSet.getString(2);
            String password = resultSet.getString(3);
            String groupCode = resultSet.getString(4);
            boolean isAdmin = resultSet.getInt(5) == 1;
            User user = new User(userId, login, password, groupCode, isAdmin);
            users.add(user);
        }
    }
}
