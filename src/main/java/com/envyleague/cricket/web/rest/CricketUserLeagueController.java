package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.domain.UserLeague;
import com.envyleague.cricket.domain.UserLeagueKey;
import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.repository.UserLeagueRepository;
import com.envyleague.cricket.service.UserService;
import com.envyleague.cricket.web.dto.LeagueDTO;
import com.envyleague.cricket.web.dto.UserLeagueDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/cricket/userLeague")
public class CricketUserLeagueController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    LeagueRepository leagueRepository;

    @Inject
    UserService userService;

    @Inject
    UserLeagueRepository userLeagueRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allLeagues(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = userService.getUserWithAuthorities();
        Set<UserLeagueDTO> currentUserLeagues = currentUser.getUserLeagues().stream().map(UserLeagueDTO::new).collect(Collectors.toSet());
        List<League> activeLeagues = leagueRepository.findByStatusInOrderByStatusDesc(Status.ACTIVE);
        List<LeagueDTO> allLeagues = activeLeagues.stream().map(LeagueDTO::new).collect(Collectors.toList());

        currentUserLeagues.forEach(s -> {
            allLeagues.stream().forEach(a -> {
                if (a.getName().equals(s.getLeague())) {
                    a.setUserLeague(s);
                }});
            });

        allLeagues.forEach(s -> s.setPlayers(null));//Not required to be passed on

        return new ResponseEntity<>(allLeagues, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUserLeague(@NotNull @RequestBody LeagueDTO leagueDTO) {
        User currentUser = userService.getUserWithAuthorities();
        League league = leagueRepository.findOneByName(leagueDTO.getName());
        if (userLeagueRepository.findOne(new UserLeagueKey(currentUser, league)) != null) {
            log.warn("User {} and League {} combination already present in Database", currentUser, league);
            return new ResponseEntity<String>("Your request is already registered.", HttpStatus.BAD_REQUEST);
        }
        UserLeague userLeague = new UserLeague(currentUser, league);
        try {
            userLeagueRepository.save(userLeague);
        } catch (Exception e) {
            log.error("Error while updating new League request ", e);
            return new ResponseEntity<String>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
