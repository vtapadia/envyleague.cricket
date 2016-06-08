package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.*;
import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.cricket.CricketPrediction;
import com.envyleague.cricket.repository.CricketMatchRepository;
import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.repository.CricketPredictionRepository;
import com.envyleague.cricket.service.UserService;
import com.envyleague.cricket.web.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
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
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/cricket/leaders")
public class CricketLeaders {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    UserService userService;

    @Inject
    LeagueRepository leagueRepository;

    @Inject
    CricketPredictionRepository cricketPredictionRepository;

    @Inject
    CricketMatchRepository cricketMatchRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLeaders(@NotNull @RequestParam("league") String league) {
        User user = userService.getUserWithAuthorities();
        if (StringUtils.isNotBlank(league)) {
            League leagueDB = leagueRepository.findOneByName(league);
            List<CricketMatch> finalizedMatches = cricketMatchRepository.findByFinalizedTrue();
            List<User> users =
                    leagueDB.getUserLeagues().stream()
                            .filter(l -> l.getStatus() == Status.ACTIVE)
                            .map(x -> x.getUser())
                            .collect(Collectors.toList());
            List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());
            if (user.getUserLeagues().stream().anyMatch(ul->ul.getUser().equals(user)) || user.getAuthorities().contains(Authority.ADMIN)) {
                if (finalizedMatches.size() != 0) {
                    //Only if you are a registered member (or ADMIN), you can view the league
                    List<CricketPrediction> predictions = cricketPredictionRepository.findByLeagueAndMatchInOrderByMatch(leagueDB, finalizedMatches);
                    predictions.stream().forEach(p->{
                        userDTOs.stream().filter(u->u.getLogin().equals(p.getPredictionKey().getUser().getLogin())).forEach(z->z.setPoints(z.getPoints()+p.getPoints()));
                    });
                }
                List<UserDTO> sortedUserDTOs = userDTOs.stream().sorted((e1,e2)->Integer.compare(e2.getPoints(), e1.getPoints())).collect(Collectors.toList());
                int rank = 0, previousScore = -1;
                for(UserDTO userDTO : sortedUserDTOs) {
                    if (previousScore==userDTO.getPoints()) {
                        userDTO.setRank(rank);
                    } else {
                        userDTO.setRank(++rank);
                        previousScore = userDTO.getPoints();
                    }
                }
                int totalPrize = leagueDB.getFee() * sortedUserDTOs.size();
                int totalWeight = sortedUserDTOs.stream().filter(u -> u.getRank() < 4).map(x -> getWeight(x.getRank())).reduce(0, (a, b) -> a + b);

                sortedUserDTOs.stream().filter(u -> u.getRank() < 4).forEach(u-> {
                    u.setPrize((totalPrize/totalWeight)*getWeight(u.getRank()));
                });
                return new ResponseEntity<>(
                        sortedUserDTOs,
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private int getWeight(int rank) {
        switch (rank) {
            case 1: return 5;
            case 2: return 3;
            case 3: return 2;
        }
        return 0;
    }

}
