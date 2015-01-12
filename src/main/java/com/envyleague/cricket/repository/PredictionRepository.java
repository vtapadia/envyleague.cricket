package com.envyleague.cricket.repository;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Match;
import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.domain.PredictionKey;
import com.envyleague.cricket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, PredictionKey> {
    @Query("from Prediction p where p.predictionKey.user=:user and p.predictionKey.match in (:match) order by p.predictionKey.match")
    public List<Prediction> findByUserAndMatchInOrderByMatch(@Param("user") User user, @Param("match") List<Match> match);

    @Query("from Prediction p where p.predictionKey.league=:league and p.predictionKey.match in (:match) order by p.predictionKey.match")
    public List<Prediction> findByLeagueAndMatchInOrderByMatch(@Param("league") League league, @Param("match") List<Match> match);

    @Query("from Prediction p where p.predictionKey.user=:user and p.predictionKey.league=:league and p.predictionKey.match=:match")
    public Prediction findOneByUserAndLeagueAndMatch(@Param("user") User user, @Param("league") League league, @Param("match") Match match);

    @Query("from Prediction p where p.predictionKey.league=:league order by p.predictionKey.match")
    public List<Prediction> findByLeagueOrderByMatch(@Param("league") League league);

    @Query("from Prediction p where p.predictionKey.match=:match")
    public List<Prediction> findByMatch(@Param("match") Match match);

}
