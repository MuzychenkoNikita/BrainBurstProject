package com.example.brainburstproject;

public class User {
    private String nickname;
    private String password;
    private String email;
    private String quizName;
    private int result;
    private int quizLength;

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.quizName = "defaultQuizName";
        this.result = 0;
        this.quizLength = 0;
    }
    public User() {this.quizName = "";}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getQuizName() {
        return quizName;
    }
    public void setQuizName(String quizName) { this.quizName = quizName; }

    public int getResult() {
        return result;
    }
    public void setResult(int result) { this.result = result; }

    public int getQuizLength() {
        return quizLength;
    }
    public void setQuizLength(int quizLength) { this.quizLength = quizLength; }
}
