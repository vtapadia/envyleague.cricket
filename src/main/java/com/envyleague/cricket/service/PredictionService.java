package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Match;
import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.domain.PredictionKey;
import com.envyleague.cricket.domain.Team;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.PredictionRepository;
import com.envyleague.cricket.repository.TeamRepository;
import com.envyleague.cricket.web.dto.PredictionDTO;
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
    public void saveOrUpdate(User user, Match match, League league, PredictionDTO predictionDTO) {
        Prediction prediction = predictionRepository.findOneByUserAndLeagueAndMatch(user, league, match);
        if (prediction == null) {
            prediction = new Prediction(new PredictionKey(user, league, match));
        }

        if (StringUtils.isNotBlank(predictionDTO.getTeamWinner()) &&
                !predictionDTO.getTeamWinner().equalsIgnoreCase("Draw")) {
            Team team = teamRepository.findOne(predictionDTO.getTeamWinner());
            prediction.setTeamWinner(team);
        } else {
            //Draw Predicted, Save null in DB
            prediction.setTeamWinner(null);
        }
        prediction.setTotalRuns(predictionDTO.getTotalRuns());
        prediction.setTotalWickets(predictionDTO.getTotalWickets());
        prediction.setTotalFours(predictionDTO.getTotalFours());
        prediction.setTotalSixes(predictionDTO.getTotalSixes());
        predictionRepository.save(prediction);
    }
}
