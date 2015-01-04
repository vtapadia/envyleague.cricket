package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Status;

import java.util.List;
import java.util.stream.Collectors;

public class LeagueDTO {
    private String name;
    private int fee;
    private UserDTO owner;
    private int maxMembers;
    private Status status;
    private String message;
    private List<UserLeagueDTO> players;

    public LeagueDTO() {}

    public LeagueDTO(League league) {
        this.name = league.getName();
        this.fee = league.getFee();
        this.owner = new UserDTO(league.getOwner());
        this.maxMembers = league.getMaxMembers();
        this.status = league.getStatus();
        this.message = league.getMessage();
        this.players = league.getUserLeagues().stream().map(UserLeagueDTO::new).collect(Collectors.toList());
    }

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

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserLeagueDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<UserLeagueDTO> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "LeagueDTO{" +
                "name='" + name + '\'' +
                ", fee=" + fee +
                '}';
    }
}
