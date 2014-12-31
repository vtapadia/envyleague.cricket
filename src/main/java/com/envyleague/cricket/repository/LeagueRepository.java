package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, String> {
}
