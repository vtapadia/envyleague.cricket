package com.envyleague.cricket.domain.cricket;

import com.envyleague.cricket.domain.Team;
import com.envyleague.cricket.domain.TournamentTeam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class CricketTournamentTeam extends TournamentTeam {

}
