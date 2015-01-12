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

    @Column(name = "total_runs")
    private Integer totalRuns;
    @Column(name = "total_wickets")
    private Integer totalWickets;
    @Column(name="total_fours")
    private Integer totalFours;
    @Column(name="total_sixes")
    private Integer totalSixes;

    private Integer points;

    @Column(name="point_scorer")
    private String pointScorer;

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

    public Integer getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(Integer totalRuns) {
        this.totalRuns = totalRuns;
    }

    public Integer getTotalWickets() {
        return totalWickets;
    }

    public void setTotalWickets(Integer totalWickets) {
        this.totalWickets = totalWickets;
    }

    public Integer getTotalFours() {
        return totalFours;
    }

    public void setTotalFours(Integer totalFours) {
        this.totalFours = totalFours;
    }

    public Integer getTotalSixes() {
        return totalSixes;
    }

    public void setTotalSixes(Integer totalSixes) {
        this.totalSixes = totalSixes;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void addPoints(int point) {
        if (this.points == null) {
            this.points = point;
        } else {
            this.points += point;
        }
    }

    public String getPointScorer() {
        return pointScorer;
    }

    public void setPointScorer(String pointScorer) {
        this.pointScorer = pointScorer;
    }

    public void addPointScorer(String pointScorer) {
        if (this.pointScorer == null) {
            this.pointScorer = "";
        }
        this.pointScorer += pointScorer;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "predictionKey=" + predictionKey +
                ", teamWinner=" + teamWinner +
                ", totalRuns=" + totalRuns +
                ", totalWickets=" + totalWickets +
                ", points=" + points +
                '}';
    }
}
