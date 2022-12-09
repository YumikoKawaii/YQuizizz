package com.example.yquizizz.database;

public class UserModel {

    private String email;
    private String username;
    private Integer exp;
    private Integer level;

    private String session;

    public UserModel(String email, String username, Integer exp, Integer level) {
        this.email = email;
        this.username = username;
        this.exp = exp;
        this.level = level;
        this.session = "0";
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Integer getExp() {
        return exp;
    }

    public Integer getLevel() {
        return level;
    }

    public String getSession() {
        return session;
    }
}
