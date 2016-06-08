package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.cricket.CricketMatch;
import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.repository.CricketMatchRepository;
import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.repository.TeamRepository;
import com.envyleague.cricket.repository.UserRepository;
import com.envyleague.cricket.service.LeagueService;
import com.envyleague.cricket.service.MatchService;
import com.envyleague.cricket.web.dto.LeagueDTO;
import com.envyleague.cricket.web.dto.MatchDTO;
import com.envyleague.cricket.web.dto.UserDTO;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/admin")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    LeagueRepository leagueRepository;

    @Inject
    LeagueService leagueService;

    @Inject
    CricketMatchRepository cricketMatchRepository;

    @Inject
    MatchService matchService;

    @Inject
    UserRepository userRepository;

    @Inject
    TeamRepository teamRepository;

    @RequestMapping(value = "/league", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allLeague(HttpServletRequest request, HttpServletResponse response) {
        List<League> leagues = leagueRepository.findByStatusInOrderByStatusDesc(Status.values());
        List<LeagueDTO> allLeagues = leagues.stream().map(LeagueDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(allLeagues,HttpStatus.OK);
    }

    @RequestMapping(value = "/league", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLeague(@RequestBody LeagueDTO leagueDTO,
                                          HttpServletRequest request, HttpServletResponse response) {
        log.info("League " + leagueDTO.getName() + " is updated by Admin. ");
        League league = leagueRepository.findOneByName(leagueDTO.getName());
        boolean statusUpdated = false;
        league.setMaxMembers(leagueDTO.getMaxMembers());
        league.setMessage(leagueDTO.getMessage());
        if (leagueDTO.getStatus() != league.getStatus()) {
            league.setStatus(leagueDTO.getStatus());
            statusUpdated = true;
        }
        leagueService.updateLeague(league, statusUpdated);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/match", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> finalizeMatch(@NotNull @RequestBody MatchDTO matchDTO) {
        CricketMatch match = cricketMatchRepository.findOne(matchDTO.getNumber());

        match.setWinner(teamRepository.findOne(matchDTO.getTeamWinner()));
        match.setTotalRuns(matchDTO.getTotalRuns());
        match.setTotalWickets(matchDTO.getTotalWickets());
        match.setTotalFours(matchDTO.getTotalFours());
        match.setTotalSixes(matchDTO.getTotalSixes());
        match.setFinalized(true);

        matchService.finalizeMatch(match);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allUsers(HttpServletRequest request, HttpServletResponse response) {
        List<UserDTO> users = userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return  new ResponseEntity<>(users,HttpStatus.OK);
    }
}
