package com.envyleague.cricket.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EL_CRIC_LEAGUE")
public class League implements Serializable {
    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String name;

    @Column(name = "fee")
    private int fee;

    @ManyToOne
    @JoinColumn(name = "tournament", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "max_members")
    private int maxMembers = 25;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "message")
    private String message;

    @JsonIgnore
    @OneToMany(mappedBy = "league", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Set<UserLeague> userLeagues = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<UserLeague> getUserLeagues() {
        return userLeagues;
    }

    public void setUserLeagues(Set<UserLeague> userLeagues) {
        this.userLeagues = userLeagues;
    }
}
