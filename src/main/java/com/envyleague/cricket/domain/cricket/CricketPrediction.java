package com.envyleague.cricket.domain.cricket;

import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.domain.Team;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class CricketPrediction extends Prediction {

    @EmbeddedId
    private CricketPredictionKey cricketPredictionKey;

    @ManyToOne
    private Team winner;

    @Column(name = "total_runs")
    private Integer totalRuns;
    @Column(name = "total_wickets")
    private Integer totalWickets;

    @Column(name="total_fours")
    private Integer totalFours;
    @Column(name="total_sixes")
    private Integer totalSixes;

    public CricketPrediction(CricketPredictionKey cricketPredictionKey) {
        this.cricketPredictionKey = cricketPredictionKey;
    }

    public CricketPrediction() {
    }

    public CricketPredictionKey getCricketPredictionKey() {
        return cricketPredictionKey;
    }

    public void setCricketPredictionKey(CricketPredictionKey cricketPredictionKey) {
        this.cricketPredictionKey = cricketPredictionKey;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
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

}
