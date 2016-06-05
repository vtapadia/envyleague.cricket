package com.envyleague.cricket.domain.football;

import com.envyleague.cricket.domain.TournamentTeam;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class FootballTournamentTeam extends TournamentTeam {

    private Integer goalsFor;

    private Integer goalsAgainst;

    public Integer getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(Integer goalsFor) {
        this.goalsFor = goalsFor;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }
}
