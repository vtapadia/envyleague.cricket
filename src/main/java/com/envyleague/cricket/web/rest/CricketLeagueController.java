package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.Authority;
import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.domain.UserLeague;
import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.service.LeagueService;
import com.envyleague.cricket.service.UserLeagueService;
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
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/cricket/league")
public class CricketLeagueController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    LeagueService leagueService;

    @Inject
    LeagueRepository leagueRepository;

    @Inject
    UserService userService;

    @Inject
    UserLeagueService userLeagueService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allMyLeague() {
        User currentUser = userService.getUserWithAuthorities();
        List<LeagueDTO> allLeagues = currentUser.getUserLeagues().stream().map(ul -> ul.getLeague()).map(LeagueDTO::new).collect(Collectors.toList());
        if (! currentUser.getAuthorities().contains(Authority.ADMIN)) {
            //Not Admin, filter others if not league owner.
            allLeagues.forEach(l -> {
                if (! l.getOwner().getLogin().equals(currentUser.getLogin())) {
                    //Not owner, only sent logged in User Details.
                    l.setPlayers(l.getPlayers().stream().filter(p -> p.getUser().equals(currentUser.getLogin())).collect(Collectors.toList()));
                }
            });
        }
        return new ResponseEntity<>(allLeagues, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMyLeague(@NotNull @RequestBody LeagueDTO leagueDTO) {
        log.info("Update league request " + leagueDTO);
        User currentUser = userService.getUserWithAuthorities();
        League league = leagueRepository.findOneByName(leagueDTO.getName());
        if (league == null) {
            log.error("SECURITY_ALERT: User {} updating invalid league {}", currentUser, leagueDTO);
            return new ResponseEntity<String>("League name not found", HttpStatus.BAD_REQUEST);
        }
        if (!league.getOwner().getLogin().equals(currentUser.getLogin())) {
            log.error("SECURITY_ALERT: User {} updating other league {}", currentUser, leagueDTO);
            return new ResponseEntity<String>("League name owned by you.", HttpStatus.BAD_REQUEST);
        }
        for(UserLeague userLeague : league.getUserLeagues()) {
            for (UserLeagueDTO userLeagueDTO : leagueDTO.getPlayers()) {
                if (userLeague.getUser().getLogin().equals(userLeagueDTO.getUser())) {
                    try {
                        userLeagueService.save(userLeague, userLeagueDTO.getStatus());
                    } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewLeague(@NotNull @RequestBody LeagueDTO leagueDTO) {
        log.info("New league request " + leagueDTO);
        if (leagueRepository.findOneByName(leagueDTO.getName()) != null) {
            return new ResponseEntity<String>("League name already in use", HttpStatus.BAD_REQUEST);
        }
        try {
            leagueService.requestNewLeague(leagueDTO.getName(), leagueDTO.getFee());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
