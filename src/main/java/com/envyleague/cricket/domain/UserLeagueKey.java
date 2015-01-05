package com.envyleague.cricket.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserLeagueKey implements Serializable{
    @ManyToOne
    @JoinColumn(name = "login")
    private User user;

    @ManyToOne
    @JoinColumn(name = "name")
    private League league;

    public UserLeagueKey() {}
    public UserLeagueKey(User user, League league) {
        this.user = user;
        this.league = league;
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
