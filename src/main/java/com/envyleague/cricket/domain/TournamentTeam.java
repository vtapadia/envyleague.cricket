package com.envyleague.cricket.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class TournamentTeam implements Serializable {

    @EmbeddedId
    private TournamentTeamKey tournamentTeamKey;

    private String group;

    private Integer points;

    public TournamentTeamKey getTournamentTeamKey() {
        return tournamentTeamKey;
    }

    public void setTournamentTeamKey(TournamentTeamKey tournamentTeamKey) {
        this.tournamentTeamKey = tournamentTeamKey;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
