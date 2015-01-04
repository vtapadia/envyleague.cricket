package com.envyleague.cricket.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "EL_USER_CRIC_LEAGUE")
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
