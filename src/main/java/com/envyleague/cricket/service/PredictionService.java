package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Match;
import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.domain.PredictionKey;
import com.envyleague.cricket.domain.Team;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.PredictionRepository;
import com.envyleague.cricket.repository.TeamRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class PredictionService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    PredictionRepository predictionRepository;

    @Inject
    TeamRepository teamRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdate(User user, Match match, League league,
                             String teamWinner, Integer totalScore, Integer totalWickets) {
        Prediction prediction = predictionRepository.findOneByUserAndLeagueAndMatch(user, league, match);
        if (prediction == null) {
            PredictionKey key = new PredictionKey();
            key.setUser(user);
            key.setMatch(match);
            key.setLeague(league);
            prediction = new Prediction(key);
        }

        if (StringUtils.isNotBlank(teamWinner)) {
            Team team = teamRepository.findOne(teamWinner);
            prediction.setTeamWinner(team);
        } else {
            prediction.setTeamWinner(null);
        }
        prediction.setTotalScore(totalScore);
        prediction.setTotalWickets(totalWickets);
        predictionRepository.save(prediction);
        log.info("prediction " + prediction);
    }
}
