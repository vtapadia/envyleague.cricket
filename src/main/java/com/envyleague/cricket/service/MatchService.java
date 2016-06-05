package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.cricket.CricketPrediction;
import com.envyleague.cricket.repository.MatchRepository;
import com.envyleague.cricket.repository.PredictionRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class MatchService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final int FULL_POINTS = 10;
    private static final int BONUS_POINTS = 5;

    private static final int RUNS_RANGE     = 20;
    private static final int SIXES_RANGE    = 2;
    private static final int FOURS_RANGE    = 2;
    private static final int WICKETS_RANGE  = 1;

    private static final String WINNER  = "Winner,";
    private static final String RUNS    = "Runs,";
    private static final String WICKETS = "Wickets,";
    private static final String FOURS   = "Fours,";
    private static final String SIXES   = "Sixes,";

    @Inject
    MatchRepository matchRepository;

    @Inject
    PredictionRepository predictionRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void finalizeMatch(CricketMatch match) {
        List<CricketPrediction> predictionsList = predictionRepository.findByMatch(match);
        predictionsList.stream().forEach(p -> {
            updatePrediction(match, p);
        });

        predictionRepository.save(predictionsList);
        matchRepository.save(match);
        log.info("Finalized CricketMatch: " + match);
    }

    private void updatePrediction(CricketMatch match, CricketPrediction p) {
        int multiplier = match.getMatchType().getMultiplier();
        p.setPoints(0);
        p.setPointScorer(StringUtils.EMPTY);
        if ((match.getTeamWinner() == null && p.getTeamWinner() == null) || //DRAW
                (match.getTeamWinner() != null && match.getTeamWinner().equals(p.getTeamWinner()))) { //Correct Winner
            p.addPoints(multiplier*FULL_POINTS);
            p.addPointScorer(WINNER);
        }
        if (p.getTotalRuns() == null) {
            p.setTotalRuns(0);
        }
        if (p.getTotalRuns() >= match.getTotalRuns()-RUNS_RANGE &&
                p.getTotalRuns() <= match.getTotalRuns()+RUNS_RANGE) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(RUNS);
        }
        if (p.getTotalSixes() == null) {
            p.setTotalSixes(0);
        }
        if (match.getTotalSixes()-SIXES_RANGE <= p.getTotalSixes() &&
                match.getTotalSixes()+SIXES_RANGE >= p.getTotalSixes()) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(SIXES);
        }
        if (p.getTotalFours() == null) {
            p.setTotalFours(0);
        }
        if (match.getTotalFours()-FOURS_RANGE <= p.getTotalFours() &&
                match.getTotalFours()+FOURS_RANGE >= p.getTotalFours()) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(FOURS);
        }
        if (p.getTotalWickets() == null) {
            p.setTotalWickets(0);
        }
        if (match.getTotalWickets()-WICKETS_RANGE <= p.getTotalWickets() &&
                match.getTotalWickets()+WICKETS_RANGE >= p.getTotalWickets()) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(WICKETS);
        }
    }

}
