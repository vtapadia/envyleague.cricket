package com.envyleague.cricket.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
public class UserLeague implements Serializable {
    public UserLeague() {
        userLeagueKey = new UserLeagueKey();
    }

    public UserLeague(User user, League league) {
        userLeagueKey = new UserLeagueKey();
        userLeagueKey.setUser(user);
        userLeagueKey.setLeague(league);
    }

    @EmbeddedId
    private UserLeagueKey userLeagueKey;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return userLeagueKey.getUser();
    }

    public void setUser(User user) {
        userLeagueKey.setUser(user);
    }

    public League getLeague() {
        return userLeagueKey.getLeague();
    }

    public void setLeague(League league) {
        userLeagueKey.setLeague(league);
    }
}
