package com.envyleague.cricket.repository;


import com.envyleague.cricket.domain.Team;
import com.envyleague.cricket.domain.cricket.CricketTournamentTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
}
