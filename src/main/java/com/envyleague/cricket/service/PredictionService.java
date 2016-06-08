package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.*;
import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.cricket.CricketPrediction;
import com.envyleague.cricket.repository.CricketPredictionRepository;
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
    CricketPredictionRepository cricketPredictionRepository;

    @Inject
    TeamRepository teamRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdate(User user, CricketMatch match, League league, PredictionDTO predictionDTO) {
        CricketPrediction prediction = cricketPredictionRepository.findOneByUserAndLeagueAndMatch(user, league, match);
        if (prediction == null) {
            prediction = new CricketPrediction(new PredictionKey(user, match, league));
        }

        if (StringUtils.isNotBlank(predictionDTO.getTeamWinner()) &&
                !predictionDTO.getTeamWinner().equalsIgnoreCase("Draw")) {
            Team team = teamRepository.findOne(predictionDTO.getTeamWinner());
            prediction.setWinner(team);
        } else {
            //Draw Predicted, Save null in DB
            prediction.setWinner(null);
        }
        prediction.setTotalRuns(predictionDTO.getTotalRuns());
        prediction.setTotalWickets(predictionDTO.getTotalWickets());
        prediction.setTotalFours(predictionDTO.getTotalFours());
        prediction.setTotalSixes(predictionDTO.getTotalSixes());
        cricketPredictionRepository.save(prediction);
    }
}
