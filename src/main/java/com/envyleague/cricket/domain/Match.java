package com.envyleague.cricket.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "EL_CRIC_MATCH")
public class Match implements Serializable {
    @Id
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tournament")
    private Tournament tournament;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "match_type")
    private MatchType matchType;

    @NotNull
    @Column(name = "start_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "team_a")
    private Team teamA;

    @ManyToOne
    @JoinColumn(name = "team_b")
    private Team teamB;

    @ManyToOne
    @JoinColumn(name = "team_winner")
    private Team teamWinner;

    @Column(name="total_score")
    private Integer totalScore;

    @Column(name="total_wickets")
    private Integer totalWickets;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
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

    @Override
    public String toString() {
        return "Match{" +
                "number=" + number +
                ", matchType=" + matchType +
                ", startTime=" + startTime +
                ", teamA=" + teamA +
                ", teamB=" + teamB +
                ", teamWinner=" + teamWinner +
                ", totalScore=" + totalScore +
                ", totalWickets=" + totalWickets +
                '}';
    }
}
