package com.envyleague.cricket.domain.cricket;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class CricketPredictionKey implements Serializable {
    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "`user`", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "match", nullable = false)
    private CricketMatch match;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "league", nullable = false)
    private League league;

    public CricketPredictionKey() {}
    public CricketPredictionKey(User user, League league, CricketMatch match) {
        this();
        this.league = league;
        this.match = match;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CricketMatch getMatch() {
        return match;
    }

    public void setMatch(CricketMatch match) {
        this.match = match;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "CricketPredictionKey{" +
                "user=" + user +
                ", match=" + match +
                ", league=" + league +
                '}';
    }
}
