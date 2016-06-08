package com.envyleague.cricket.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class Prediction implements Serializable {
    @EmbeddedId
    private PredictionKey predictionKey;

    @ManyToOne
    private Team winner;

    private Integer points;

    private String pointScorer;

    public Prediction(PredictionKey predictionKey) {
        this.predictionKey = predictionKey;
    }

    public Prediction() {
    }

    public void addPoints(int point) {
        if (this.points == null) {
            this.points = point;
        } else {
            this.points += point;
        }
    }

    public void addPointScorer(String pointScorer) {
        if (this.pointScorer == null) {
            this.pointScorer = "";
        }
        this.pointScorer += pointScorer;
    }

    public PredictionKey getPredictionKey() {
        return predictionKey;
    }

    public void setPredictionKey(PredictionKey predictionKey) {
        this.predictionKey = predictionKey;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPointScorer() {
        return pointScorer;
    }

    public void setPointScorer(String pointScorer) {
        this.pointScorer = pointScorer;
    }
}
