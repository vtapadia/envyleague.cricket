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
        Map<League, List<Prediction>> leaguePredictionsMap = predictionsList.stream().collect(Collectors.groupingBy(x->x.getPredictionKey().getLeague()));

        leaguePredictionsMap.entrySet().stream().forEach(entry -> updatePredictions(match, entry.getValue()));
        predictionRepository.save(predictionsList);
        matchRepository.save(match);
        log.info("Finalized Match: " + match);
    }

    private void updatePredictions(Match match, List<Prediction> predictions) {
        int multiplier = match.getMatchType().getMultiplier();
        //For FULL_POINTS in match
        int closestDiffRuns = Integer.MAX_VALUE,closestDiffWickets = Integer.MAX_VALUE,closestDiffFours = Integer.MAX_VALUE,closestDiffSixes = Integer.MAX_VALUE;
        for (Prediction p: predictions) {
            if ((match.getTeamWinner() == null && p.getTeamWinner() == null) || //DRAW
                    match.getTeamWinner().equals(p.getTeamWinner())) { //Correct Winner
                p.addPoints(multiplier*FULL_POINTS);
                p.addPointScorer(WINNER);
            }
            if (p.getTotalRuns() != null) {
                int diffRuns = Math.abs(match.getTotalRuns() - p.getTotalRuns());
                if (diffRuns < closestDiffRuns) {closestDiffRuns = diffRuns;}
                if (diffRuns == 0) {//Perfect Match so give FULL points
                    p.addPoints(multiplier * FULL_POINTS);
                    p.addPointScorer(RUNS);
                }
            }
            if (p.getTotalWickets() != null) {
                int diffWickets = Math.abs(match.getTotalWickets() - p.getTotalWickets());
                if (diffWickets<closestDiffWickets) {closestDiffWickets = diffWickets;}
                if (diffWickets == 0) {
                    p.addPoints(multiplier * FULL_POINTS);
                    p.addPointScorer(WICKETS);
                }
            }
            if (p.getTotalFours() != null) {
                int diffFours = Math.abs(match.getTotalFours() - p.getTotalFours());
                if (diffFours<closestDiffFours) {closestDiffFours = diffFours;}
                if (diffFours == 0) {
                    p.addPoints(multiplier * FULL_POINTS);
                    p.addPointScorer(FOURS);
                }
            }
            if (p.getTotalSixes() != null) {
                int diffSixes = Math.abs(match.getTotalSixes() - p.getTotalSixes());
                if (diffSixes<closestDiffSixes) {closestDiffSixes = diffSixes;}
                if (diffSixes == 0) {
                    p.addPoints(multiplier * FULL_POINTS);
                    p.addPointScorer(SIXES);
                }
            }
        }
        //For BONUS_POINTS, in case no one got an exact match.
        for (Prediction p: predictions) {
            if (closestDiffRuns != 0) {
                if (p.getTotalRuns() != null &&
                        Math.abs(match.getTotalRuns() - p.getTotalRuns()) == closestDiffRuns) {
                    p.addPoints(multiplier * BONUS_POINTS);
                    p.addPointScorer(RUNS);
                }
            }
            if (closestDiffWickets != 0) {
                if (p.getTotalWickets() != null &&
                        Math.abs(match.getTotalWickets() - p.getTotalWickets()) == closestDiffWickets) {
                    p.addPoints(multiplier * BONUS_POINTS);
                    p.addPointScorer(WICKETS);
                }
            }
            if (closestDiffFours != 0) {
                if (p.getTotalFours() != null &&
                        Math.abs(match.getTotalFours() - p.getTotalFours()) == closestDiffFours) {
                    p.addPoints(multiplier * BONUS_POINTS);
                    p.addPointScorer(FOURS);
                }
            }
            if (closestDiffSixes != 0) {
                if (p.getTotalSixes() != null &&
                        Math.abs(match.getTotalSixes() - p.getTotalSixes()) == closestDiffSixes) {
                    p.addPoints(multiplier * BONUS_POINTS);
                    p.addPointScorer(SIXES);
                }
            }
        }
    }

}
