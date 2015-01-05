package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.domain.UserLeague;

public class UserLeagueDTO {
    private String user;
    private String league;
    private String name;
    private String email;
    private Status status;

    public UserLeagueDTO() {}
    public UserLeagueDTO(UserLeague userLeague) {
        User user = userLeague.getUser();
        this.user = user.getLogin();
        this.name = user.getName();
        this.email = user.getEmail();
        this.status = userLeague.getStatus();
        this.league = userLeague.getLeague().getName();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
