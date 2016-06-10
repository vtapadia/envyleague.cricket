package com.envyleague.cricket.domain.football;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class FootballPrediction {
    @EmbeddedId
    private FootballPredictionKey footballPredictionKey;

    @Column(name = "team_a_score")
    private Integer teamAScore;

    @Column(name = "team_b_score")
    private Integer teamBScore;

    public FootballPredictionKey getFootballPredictionKey() {
        return footballPredictionKey;
    }

    public void setFootballPredictionKey(FootballPredictionKey footballPredictionKey) {
        this.footballPredictionKey = footballPredictionKey;
    }

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
}
