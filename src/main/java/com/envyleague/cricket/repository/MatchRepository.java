package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.cricket.CricketMatch;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<CricketMatch, Integer> {
    public List<CricketMatch> findByStartTimeAfter(LocalDateTime startDate);
    public List<CricketMatch> findByStartTimeBefore(LocalDateTime startDate);
    public List<CricketMatch> findByFinalizedTrue();
}
