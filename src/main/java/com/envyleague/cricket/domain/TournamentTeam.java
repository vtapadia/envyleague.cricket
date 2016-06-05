package com.envyleague.cricket.domain;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class TournamentTeam implements Serializable {
    @ManyToOne
    private Tournament tournament;

    @ManyToOne
    private Team team;

    private String group;

    private Integer points;
}
