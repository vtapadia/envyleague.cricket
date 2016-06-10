package com.envyleague.cricket.domain.football;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.User;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class FootballPredictionKey implements Serializable {

    @ManyToOne
    private User user;

    @ManyToOne
    private FootballMatch match;

    @ManyToOne
    private League league;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FootballMatch getMatch() {
        return match;
    }

    public void setMatch(FootballMatch match) {
        this.match = match;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FootballPredictionKey that = (FootballPredictionKey) o;

        if (!user.equals(that.user)) return false;
        if (!match.equals(that.match)) return false;
        return league.equals(that.league);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + match.hashCode();
        result = 31 * result + league.hashCode();
        return result;
    }
}
