package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.Match;
import com.envyleague.cricket.domain.MatchType;

import java.util.Date;
import java.util.List;

public class MatchDTO {
    private Integer number;
    private String tournament;
    private MatchType matchType;
    private Date startTime;
    private String teamA;
    private String teamB;
    private String teamWinner;
    private Integer totalScore;
    private Integer totalWickets;
    private List<PredictionDTO> predictions;

    public MatchDTO() {}

    public MatchDTO(Match match) {
        this.number = match.getNumber();
        this.tournament = match.getTournament().getName();
        this.matchType = match.getMatchType();
        this.startTime = (match.getStartTime()==null)?null:match.getStartTime().toDate();
        this.teamA = (match.getTeamA() == null)?null:match.getTeamA().getName();
        this.teamB = (match.getTeamB() == null)?null:match.getTeamB().getName();
        this.teamWinner = (match.getTeamWinner() == null) ? null:match.getTeamWinner().getName();
        this.totalScore = match.getTotalScore();
        this.totalWickets = match.getTotalWickets();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
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

    public List<PredictionDTO> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionDTO> predictions) {
        this.predictions = predictions;
    }
}
