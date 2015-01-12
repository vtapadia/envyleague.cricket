package com.envyleague.cricket.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class PredictionKey implements Serializable {
    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "`user`", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "match", nullable = false)
    private Match match;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "league", nullable = false)
    private League league;

    public PredictionKey() {}
    public PredictionKey(User user, League league, Match match) {
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

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
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
        return "PredictionKey{" +
                "user=" + user +
                ", match=" + match +
                ", league=" + league +
                '}';
    }
}
