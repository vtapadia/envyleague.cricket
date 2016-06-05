package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.*;
import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.cricket.CricketPrediction;
import com.envyleague.cricket.domain.cricket.CricketPredictionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PredictionRepository extends JpaRepository<CricketPrediction, CricketPredictionKey> {
    @Query("from Prediction p where p.predictionKey.user=:user and p.predictionKey.match in (:match) order by p.predictionKey.match")
    public List<CricketPrediction> findByUserAndMatchInOrderByMatch(@Param("user") User user, @Param("match") List<CricketMatch> match);

    @Query("from Prediction p where p.predictionKey.league=:league and p.predictionKey.match in (:match) order by p.predictionKey.match")
    public List<CricketPrediction> findByLeagueAndMatchInOrderByMatch(@Param("league") League league, @Param("match") List<CricketMatch> match);

    @Query("from Prediction p where p.predictionKey.user=:user and p.predictionKey.league=:league and p.predictionKey.match=:match")
    public CricketPrediction findOneByUserAndLeagueAndMatch(@Param("user") User user, @Param("league") League league, @Param("match") CricketMatch match);

    @Query("from Prediction p where p.predictionKey.league=:league order by p.predictionKey.match")
    public List<CricketPrediction> findByLeagueOrderByMatch(@Param("league") League league);

    @Query("from Prediction p where p.predictionKey.match=:match")
    public List<CricketPrediction> findByMatch(@Param("match") CricketMatch match);

}
