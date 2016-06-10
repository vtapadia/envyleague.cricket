package com.envyleague.cricket.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Prediction implements Serializable {
    private Integer points;

    @Column(name = "points_scorer")
    private String pointsScorer;


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
        if (this.pointsScorer == null) {
            this.pointsScorer = "";
        }
        this.pointsScorer += pointScorer;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPointsScorer() {
        return pointsScorer;
    }

    public void setPointsScorer(String pointsScorer) {
        this.pointsScorer = pointsScorer;
    }
}
