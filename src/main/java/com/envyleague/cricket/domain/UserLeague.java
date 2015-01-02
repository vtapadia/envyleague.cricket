package com.envyleague.cricket.domain;

import javax.persistence.Column;
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

    }

    public UserLeague(User user, League league) {
        this.user = user;
        this.league = league;
    }
    @ManyToOne
    @JoinColumn(name = "login")
    @Id
    private User user;

    @ManyToOne
    @JoinColumn(name = "name")
    @Id
    private League league;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
