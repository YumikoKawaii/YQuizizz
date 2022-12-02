package com.example.yquizizz.leaderboard;

public class Guest {
    private String name;
    private long rank;
    private long exp;
    private long level;
    private String avtUrl;

    public Guest(String name, long rank, long exp, long level, String avtUrl) {
        this.name = name;
        this.rank = rank;
        this.exp = exp;
        this.level = level;
        this.avtUrl = avtUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl = avtUrl;
    }

    public String getAvtUrl() {
        return avtUrl;
    }
}

