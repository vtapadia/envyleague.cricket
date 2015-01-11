package com.envyleague.cricket.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    }

    public Prediction(PredictionKey key) {
        this.predictionKey = key;
    }

    public PredictionKey getPredictionKey() {
        return predictionKey;
    }

    public void setPredictionKey(PredictionKey predictionKey) {
        this.predictionKey = predictionKey;
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

    @Override
    public String toString() {
        return "Prediction{" +
                "predictionKey=" + predictionKey +
                ", teamWinner=" + teamWinner +
                ", totalScore=" + totalScore +
                ", totalWickets=" + totalWickets +
                ", points=" + points +
                '}';
    }
}
