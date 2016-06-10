package com.envyleague.cricket.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TournamentTeamKey implements Serializable {
    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Team team;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TournamentTeamKey that = (TournamentTeamKey) o;

        if (!tournament.equals(that.tournament)) return false;
        return team.equals(that.team);

    }

    @Override
    public int hashCode() {
        int result = tournament.hashCode();
        result = 31 * result + team.hashCode();
        return result;
    }
}
