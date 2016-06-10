package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.cricket.CricketPrediction;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.CricketMatchRepository;
import com.envyleague.cricket.repository.CricketPredictionRepository;
import com.envyleague.cricket.service.UserService;
import com.envyleague.cricket.web.dto.MatchDTO;
import com.envyleague.cricket.web.dto.PredictionDTO;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rest/cricket/match")
public class CricketMatchController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    UserService userService;

    @Inject
    CricketMatchRepository cricketMatchRepository;

    @Inject
    CricketPredictionRepository cricketPredictionRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMatchesWithPredictions(
            @RequestParam("predictions") boolean providePredictions,
            @RequestParam("future") boolean future) {
        User user = userService.getUserWithAuthorities();
        //TODO May be I can pass for all the matches
        List<CricketMatch> matches = null;
        if (future) {
            matches = cricketMatchRepository.findByStartTimeAfter(LocalDateTime.now());
        } else {
            matches = cricketMatchRepository.findByStartTimeBefore(LocalDateTime.now());
        }
        //matches = cricketMatchRepository.findAll();//TODO DEV CODE
        List<MatchDTO> matchDTOs = matches.stream().map(MatchDTO::new).collect(toList());
        if (providePredictions && matches.size()>0) {
            List<CricketPrediction> predictions = cricketPredictionRepository.findByUserAndMatchInOrderByMatch(user, matches);
            List<PredictionDTO> predictionDTOs = predictions.stream().map(PredictionDTO::new).collect(toList());

            matchDTOs.forEach(
                    m->m.setPredictions(
                            predictionDTOs.stream().filter(
                                    p -> p.getMatch().intValue() == m.getNumber().intValue()).collect(toList())));
        }
        return new ResponseEntity(matchDTOs, HttpStatus.OK);
    }

}
