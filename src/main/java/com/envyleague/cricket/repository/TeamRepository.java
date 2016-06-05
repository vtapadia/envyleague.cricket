package com.envyleague.cricket.repository;


import com.envyleague.cricket.domain.cricket.CricketTournamentTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<CricketTournamentTeam, String> {
}
