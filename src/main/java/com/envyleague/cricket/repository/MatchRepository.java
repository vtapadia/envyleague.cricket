package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.Match;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    public List<Match> findByStartTimeAfter(LocalDateTime startDate);
}
