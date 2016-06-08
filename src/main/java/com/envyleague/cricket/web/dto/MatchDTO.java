package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.cricket.CricketMatch;
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
    private Integer totalRuns;
    private Integer totalWickets;
    private Integer totalFours;
    private Integer totalSixes;
    private boolean finalized;
    private List<PredictionDTO> predictions;

    public MatchDTO() {}

    public MatchDTO(CricketMatch match) {
        this.number = match.getId();
        this.tournament = match.getTournament().getName();
        this.matchType = match.getMatchType();
        this.startTime = (match.getStartTime()==null)?null:match.getStartTime().toDate();
        this.teamA = (match.getTeamA() == null)?null:match.getTeamA().getName();
        this.teamB = (match.getTeamB() == null)?null:match.getTeamB().getName();
        this.teamWinner = (match.getWinner() == null) ? null:match.getWinner().getName();
        this.totalRuns = match.getTotalRuns();
        this.totalWickets = match.getTotalWickets();
        this.totalFours = match.getTotalFours();
        this.totalSixes = match.getTotalSixes();
        this.finalized = match.isFinalized();
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

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
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

    public List<PredictionDTO> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionDTO> predictions) {
        this.predictions = predictions;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "number=" + number +
                ", tournament='" + tournament + '\'' +
                ", matchType=" + matchType +
                ", startTime=" + startTime +
                ", teamA='" + teamA + '\'' +
                ", teamB='" + teamB + '\'' +
                ", teamWinner='" + teamWinner + '\'' +
                ", totalRuns=" + totalRuns +
                ", totalWickets=" + totalWickets +
                ", totalFours=" + totalFours +
                ", totalSixes=" + totalSixes +
                ", finalized=" + finalized +
                '}';
    }
}
