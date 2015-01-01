package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.service.LeagueService;
import com.envyleague.cricket.web.dto.LeagueDTO;
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

@RestController
@RequestMapping("/rest/cricket")
public class CricketController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    LeagueService leagueService;

    @Inject
    LeagueRepository leagueRepository;

    @RequestMapping(value = "/league", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewLeague(@NotNull @RequestBody LeagueDTO leagueDTO) {
        log.info("New league request " + leagueDTO);
        if (leagueRepository.findOneByName(leagueDTO.getName()) != null) {
            return new ResponseEntity<String>("League name already in use", HttpStatus.BAD_REQUEST);
        }
        leagueService.requestNewLeague(leagueDTO.getName(), leagueDTO.getFee());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
