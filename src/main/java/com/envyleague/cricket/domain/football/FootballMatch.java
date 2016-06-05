package com.envyleague.cricket.domain.football;

import com.envyleague.cricket.domain.Match;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class FootballMatch extends Match {
    private Integer teamAScore;
    private Integer teamBScore;

    private Integer teamAPenalty;
    private Integer teamBPenalty;

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(Integer teamAScore) {
        this.teamAScore = teamAScore;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(Integer teamBScore) {
        this.teamBScore = teamBScore;
    }

    public Integer getTeamAPenalty() {
        return teamAPenalty;
    }

    public void setTeamAPenalty(Integer teamAPenalty) {
        this.teamAPenalty = teamAPenalty;
    }

    public Integer getTeamBPenalty() {
        return teamBPenalty;
    }

    public void setTeamBPenalty(Integer teamBPenalty) {
        this.teamBPenalty = teamBPenalty;
    }
}
