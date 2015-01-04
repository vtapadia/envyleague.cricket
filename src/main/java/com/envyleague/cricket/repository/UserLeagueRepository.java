package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.UserLeague;
import com.envyleague.cricket.domain.UserLeagueKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLeagueRepository extends JpaRepository<UserLeague, UserLeagueKey> {
}
