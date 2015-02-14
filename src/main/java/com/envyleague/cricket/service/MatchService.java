package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Match;
import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.repository.MatchRepository;
import com.envyleague.cricket.repository.PredictionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void finalizeMatch(Match match) {
        List<Prediction> predictionsList = predictionRepository.findByMatch(match);
        predictionsList.stream().forEach(p -> {
            updatePrediction(match, p);
        });

        predictionRepository.save(predictionsList);
        matchRepository.save(match);
        log.info("Finalized Match: " + match);
    }

    private void updatePrediction(Match match, Prediction p) {
        int multiplier = match.getMatchType().getMultiplier();
        if ((match.getTeamWinner() == null && p.getTeamWinner() == null) || //DRAW
                match.getTeamWinner().equals(p.getTeamWinner())) { //Correct Winner
            p.addPoints(multiplier*FULL_POINTS);
            p.addPointScorer(WINNER);
        }
        if (p.getTotalRuns() >= match.getTotalRuns()-RUNS_RANGE &&
                p.getTotalRuns() <= match.getTotalRuns()+RUNS_RANGE) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(RUNS);
        }
        if (match.getTotalSixes()-SIXES_RANGE <= p.getTotalSixes() &&
                match.getTotalSixes()+SIXES_RANGE >= p.getTotalSixes()) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(SIXES);
        }
        if (match.getTotalFours()-FOURS_RANGE <= p.getTotalFours() &&
                match.getTotalFours()+FOURS_RANGE >= p.getTotalFours()) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(FOURS);
        }
        if (match.getTotalWickets()-WICKETS_RANGE <= p.getTotalWickets() &&
                match.getTotalWickets()+WICKETS_RANGE >= p.getTotalWickets()) {
            p.addPoints(multiplier*BONUS_POINTS);
            p.addPointScorer(WICKETS);
        }
    }

}
