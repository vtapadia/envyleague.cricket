package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Match;
import com.envyleague.cricket.domain.Prediction;
import com.envyleague.cricket.domain.PredictionKey;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.repository.MatchRepository;
import com.envyleague.cricket.repository.PredictionRepository;
import com.envyleague.cricket.service.PredictionService;
import com.envyleague.cricket.service.UserService;
import com.envyleague.cricket.web.dto.MatchDTO;
import com.envyleague.cricket.web.dto.PredictionDTO;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rest/cricket/prediction")
public class CricketPredictionController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    UserService userService;

    @Inject
    MatchRepository matchRepository;

    @Inject
    PredictionRepository predictionRepository;

    @Inject
    LeagueRepository leagueRepository;

    @Inject
    PredictionService predictionService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMatchPrediction(@RequestBody PredictionDTO predictionDTO) {
        User user = userService.getUserWithAuthorities();
        if (StringUtils.isBlank(predictionDTO.getUser())) {
            predictionDTO.setUser(user.getLogin());
        }
        Match match = matchRepository.findOne(predictionDTO.getMatch());
        if (match.getStartTime().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("Match is started. update not allowed now", HttpStatus.BAD_REQUEST);
        }
        League league = leagueRepository.findOneByName(predictionDTO.getLeague());
        predictionService.saveOrUpdate(user, match, league, predictionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
