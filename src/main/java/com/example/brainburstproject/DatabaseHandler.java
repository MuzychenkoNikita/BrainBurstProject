package com.example.brainburstproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import static com.example.brainburstproject.MenuPageController.myObject;

public class DatabaseHandler extends DatabaseConfig {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + DatabaseConst.USER_TABLE + "("
                + DatabaseConst.USER_NICKNAME + ","
                + DatabaseConst.USER_PASSWORD + ")"
                + "VALUES(?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getNickname());
            prSt.setString(2, user.getPassword());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + DatabaseConst.USER_TABLE + " WHERE " +
                DatabaseConst.USER_NICKNAME + "=? AND " + DatabaseConst.USER_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getNickname());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resSet;
    }

    public boolean doesUserExist(String nickname) {
        String select = "SELECT * FROM " + DatabaseConst.USER_TABLE + " WHERE " +
                DatabaseConst.USER_NICKNAME + "=?";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, nickname);

            try (ResultSet resSet = prSt.executeQuery()) {
                return resSet.next();  // Return true if a record is found
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Handle the exception appropriately
        }

        return false;  // Return false if an exception occurs
    }


    public ArrayList<String> getQuizTables() {
        ArrayList<String> quizTablesList = new ArrayList<>();
        String select = "SELECT * FROM " + DatabaseConst.QUIZ_TABLES_TABLE;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                String quizName = resultSet.getString(DatabaseConst.QUIZ_TABLES_QUIZ_NAME);
                quizTablesList.add(quizName);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return quizTablesList;
    }

    public ArrayList<String> getQuestionData() {
        ArrayList<String> questionData = new ArrayList<>();
        String select = "SELECT * FROM " + myObject.getQuizName();

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                String quizData = resultSet.getString(DatabaseConst.CURRENT_QUIZ_QUESTION);
                questionData.add(quizData);
                quizData = resultSet.getString(DatabaseConst.CURRENT_QUIZ_OPTION1);
                questionData.add(quizData);
                quizData = resultSet.getString(DatabaseConst.CURRENT_QUIZ_OPTION2);
                questionData.add(quizData);
                quizData = resultSet.getString(DatabaseConst.CURRENT_QUIZ_OPTION3);
                questionData.add(quizData);
                quizData = resultSet.getString(DatabaseConst.CURRENT_QUIZ_OPTIOB4);
                questionData.add(quizData);
                quizData = resultSet.getString(DatabaseConst.CURRENT_QUIZ_ANSWER);
                questionData.add(quizData);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return questionData;
    }

    public void saveUserScore() {
        String leaderboardTable = myObject.getQuizName() + "_Leaderboard";
        String select = "SELECT * FROM " + leaderboardTable +
                " WHERE " + DatabaseConst.USER_NICKNAME + "=?";
        String update = "UPDATE " + leaderboardTable +
                " SET " + DatabaseConst.USER_SCORE + "=?" +
                " WHERE " + DatabaseConst.USER_NICKNAME + "=?";
        String insert = "INSERT INTO " + leaderboardTable + "(" +
                DatabaseConst.USER_NICKNAME + "," +
                DatabaseConst.USER_SCORE + ")" +
                " VALUES(?,?)";

        try {
            Connection connection = getDbConnection();

            PreparedStatement selectStatement = connection.prepareStatement(select);
            selectStatement.setString(1, myObject.getNickname());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                PreparedStatement updateStatement = connection.prepareStatement(update);
                updateStatement.setInt(1, myObject.getResult());
                updateStatement.setString(2, myObject.getNickname());
                updateStatement.executeUpdate();
            } else {
                PreparedStatement insertStatement = connection.prepareStatement(insert);
                insertStatement.setString(1, myObject.getNickname());
                insertStatement.setInt(2, myObject.getResult());
                insertStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLeaderboard() {
        StringBuilder leaderboard = new StringBuilder();
        String select = "SELECT " + DatabaseConst.USER_NICKNAME + ", " + DatabaseConst.USER_SCORE +
                " FROM " + myObject.getQuizName() + "_Leaderboard" +
                " ORDER BY " + DatabaseConst.USER_SCORE + " DESC" +
                " LIMIT 5";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();

            int position = 1;
            while (resultSet.next()) {
                String nickname = resultSet.getString(DatabaseConst.USER_NICKNAME);
                int score = resultSet.getInt(DatabaseConst.USER_SCORE);

                leaderboard.append(position).append(". ").append(nickname).append(" - ").append(score).append("\n");
                position++;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return leaderboard.toString();
    }

    public String getUserQuizScore() {
        String leaderboardTable = myObject.getQuizName() + "_Leaderboard";
        String select = "SELECT " + DatabaseConst.USER_SCORE +
                " FROM " + leaderboardTable +
                " WHERE " + DatabaseConst.USER_NICKNAME + "=?";

        try {
            Connection connection = getDbConnection();

            PreparedStatement selectStatement = connection.prepareStatement(select);
            selectStatement.setString(1, myObject.getNickname());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int score = resultSet.getInt(DatabaseConst.USER_SCORE);
                return "Your previous score was " + score;
            } else {
                return "You haven't played this quiz yet";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
