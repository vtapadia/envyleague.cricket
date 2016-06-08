package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.cricket.CricketMatch;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CricketMatchRepository extends JpaRepository<CricketMatch, Integer> {
    List<CricketMatch> findByStartTimeAfter(LocalDateTime startDate);
    List<CricketMatch> findByStartTimeBefore(LocalDateTime startDate);
    List<CricketMatch> findByFinalizedTrue();
}
