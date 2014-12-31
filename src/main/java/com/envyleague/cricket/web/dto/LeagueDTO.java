package com.envyleague.cricket.web.dto;

public class LeagueDTO {
    private String name;
    private int fee;

    public LeagueDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "LeagueDTO{" +
                "name='" + name + '\'' +
                ", fee=" + fee +
                '}';
    }
}
