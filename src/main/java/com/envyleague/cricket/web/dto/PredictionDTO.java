package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.Prediction;

public class PredictionDTO {
    private String user;
    private String league;
    private Integer match;
    private String teamWinner;
    private Integer totalScore;
    private Integer totalWickets;
    private boolean allLeague = false;

    public PredictionDTO() {}

    public PredictionDTO(Prediction prediction) {
        this.user = prediction.getPredictionKey().getUser().getLogin();
        this.league = prediction.getPredictionKey().getLeague().getName();
        this.match = prediction.getPredictionKey().getMatch().getNumber();
        this.teamWinner = (prediction.getTeamWinner()==null) ? "" : prediction.getTeamWinner().getName();
        this.totalScore = prediction.getTotalScore();
        this.totalWickets = prediction.getTotalWickets();
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

    public boolean isAllLeague() {
        return allLeague;
    }

    public void setAllLeague(boolean allLeague) {
        this.allLeague = allLeague;
    }
}
