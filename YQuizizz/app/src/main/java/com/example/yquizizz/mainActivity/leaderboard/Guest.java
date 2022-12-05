package com.example.yquizizz.mainActivity.leaderboard;

public class Guest {
    private String username;
    private Integer userRank;
    private Integer userCurrentExp;
    private Integer userLevel;
    private String avtUrl;

    public Guest(String username, Integer userRank, Integer userCurrentExp, Integer userLevel) {
        this.username = username;
        this.userRank = userRank;
        this.userCurrentExp = userCurrentExp;
        this.userLevel = userLevel;
    }

    public String getUsername() {
        return username;
    }

    public Integer getUserRank() {
        return userRank;
    }

    public Integer getUserTotalExp() {
        Integer total = 0;
        Integer base = 400;
        for (int i = 1;i < userLevel;i++)
        {
            if (i%10 == 0) base += 100;
            total += base;
        }
        total += userCurrentExp;
        return total;
    }

    public Integer getUserLevel() {
        return userLevel;
    }
}

