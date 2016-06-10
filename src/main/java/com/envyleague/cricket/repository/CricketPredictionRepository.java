package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.*;
import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.cricket.CricketPrediction;
import com.envyleague.cricket.domain.cricket.CricketPredictionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CricketPredictionRepository extends JpaRepository<CricketPrediction, CricketPredictionKey> {
    @Query("from CricketPrediction p where p.cricketPredictionKey.user=:user and p.cricketPredictionKey.match in (:match) order by p.cricketPredictionKey.match")
    public List<CricketPrediction> findByUserAndMatchInOrderByMatch(@Param("user") User user, @Param("match") List<CricketMatch> match);

    @Query("from CricketPrediction p where p.cricketPredictionKey.league=:league and p.cricketPredictionKey.match in (:match) order by p.cricketPredictionKey.match")
    public List<CricketPrediction> findByLeagueAndMatchInOrderByMatch(@Param("league") League league, @Param("match") List<CricketMatch> match);

    @Query("from CricketPrediction p where p.cricketPredictionKey.user=:user and p.cricketPredictionKey.league=:league and p.cricketPredictionKey.match=:match")
    public CricketPrediction findOneByUserAndLeagueAndMatch(@Param("user") User user, @Param("league") League league, @Param("match") CricketMatch match);

    @Query("from CricketPrediction p where p.cricketPredictionKey.league=:league order by p.cricketPredictionKey.match")
    public List<CricketPrediction> findByLeagueOrderByMatch(@Param("league") League league);

    @Query("from CricketPrediction p where p.cricketPredictionKey.match=:match")
    public List<CricketPrediction> findByMatch(@Param("match") CricketMatch match);

}
