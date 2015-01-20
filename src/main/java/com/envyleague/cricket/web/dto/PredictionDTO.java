package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.Prediction;

public class PredictionDTO {
    private String user;
    private String league;
    private Integer match;
    private String teamWinner;
    private Integer totalRuns;
    private Integer totalWickets;
    private Integer totalFours;
    private Integer totalSixes;
    private Integer points;
    private String pointScorer;
    private boolean allLeague = false;

    public PredictionDTO() {}

    public PredictionDTO(Prediction prediction) {
        this.user = prediction.getPredictionKey().getUser().getLogin();
        this.league = prediction.getPredictionKey().getLeague().getName();
        this.match = prediction.getPredictionKey().getMatch().getNumber();
        this.teamWinner = (prediction.getTeamWinner()==null) ? "" : prediction.getTeamWinner().getName();
        this.totalRuns = prediction.getTotalRuns();
        this.totalWickets = prediction.getTotalWickets();
        this.totalFours = prediction.getTotalFours();
        this.totalSixes = prediction.getTotalSixes();
        this.points = prediction.getPoints();
        this.pointScorer = prediction.getPointScorer();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match= match;
    }

    public String getTeamWinner() {
        return teamWinner;
    }

    public void setTeamWinner(String teamWinner) {
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

    public String getPointScorer() {
        return pointScorer;
    }

    public void setPointScorer(String pointScorer) {
        this.pointScorer = pointScorer;
    }

    public boolean isAllLeague() {
        return allLeague;
    }

    public void setAllLeague(boolean allLeague) {
        this.allLeague = allLeague;
    }
}
