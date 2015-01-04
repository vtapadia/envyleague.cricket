package com.envyleague.cricket.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "EL_CRIC_PREDICTION")
public class Prediction implements Serializable {
    @EmbeddedId
    private PredictionKey predictionKey;

    @ManyToOne
    @JoinColumn(name = "team_winner")
    private Team teamWinner;

    @Column(name = "total_score")
    private Integer totalScore;
    @Column(name = "total_wickets")
    private Integer totalWickets;
    private Integer points;

    public Prediction() {
        this.predictionKey = new PredictionKey();
    }

    public User getUser() {
        return predictionKey.getUser();
    }

    public void setUser(User user) {
        predictionKey.setUser(user);
    }

    public Match getMatch() {
        return predictionKey.getMatch();
    }

    public void setMatch(Match match) {
        predictionKey.setMatch(match);
    }

    public League getLeague() {
        return predictionKey.getLeague();
    }

    public void setLeague(League league) {
        predictionKey.setLeague(league);
    }

    public Team getTeamWinner() {
        return teamWinner;
    }

    public void setTeamWinner(Team teamWinner) {
        this.teamWinner = teamWinner;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalWickets() {
        return totalWickets;
    }

    public void setTotalWickets(Integer totalWickets) {
        this.totalWickets = totalWickets;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
