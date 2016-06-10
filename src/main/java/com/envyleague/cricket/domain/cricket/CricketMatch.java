package com.envyleague.cricket.domain.cricket;

import com.envyleague.cricket.domain.Match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class CricketMatch extends Match {

    @Column(name="total_runs")
    private Integer totalRuns;

    @Column(name="total_wickets")
    private Integer totalWickets;

    @Column(name="total_fours")
    private Integer totalFours;

    @Column(name="total_sixes")
    private Integer totalSixes;

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
