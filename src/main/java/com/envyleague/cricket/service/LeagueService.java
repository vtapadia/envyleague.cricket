package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.League;
import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.domain.Tournament;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.LeagueRepository;
import com.envyleague.cricket.repository.TournamentRepository;
import com.envyleague.cricket.repository.UserRepository;
import com.envyleague.cricket.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class LeagueService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    LeagueRepository leagueRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    TournamentRepository tournamentRepository;

    @Inject
    MailService mailService;

    @Inject
    FacebookService facebookService;

    public void requestNewLeague(String leagueName, int fee) {
        User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
        log.info("User " + currentUser.getLogin() + " requested for a new League " + leagueName);
        Tournament tournament = tournamentRepository.findOneByStatus(Status.ACTIVE);
        if (tournament != null) {
            League league = new League();
            league.setOwner(currentUser);
            league.setTournament(tournament);
            league.setFee(fee);
            league.setName(leagueName);
            leagueRepository.save(league);
        } else {
            throw new ServiceException("Tournament not started for league registration.");
        }
    }

    public void updateLeague(League league, boolean statusUpdated) {
        leagueRepository.save(league);
        if (statusUpdated) {
            //Send a notification to the owner.
            StringBuilder sb = new StringBuilder();
            sb.append("<p>Dear " + league.getOwner().getLogin() + ",");
            sb.append("<p>Your LEAGUE status has been updated to " + league.getStatus()+ ". <br> ");
            if (StringUtils.isNotBlank(league.getMessage())) {
                sb.append("Message from Admin: " + league.getMessage()+ ". <br> ");
            }
            sb.append("<p>Do get in touch in case of any concerns.<br>");
            sb.append("<p>Thanks<br>Nidhi and Varesh");

            mailService.sendEmail(league.getOwner().getEmail(), "Notification from www.envyLeague.com",
                    sb.toString());
            if (StringUtils.isNotBlank(league.getOwner().getFacebookUserId())) {
                facebookService.sendNotification(league.getOwner(), "Your League " + league.getName() + " has been updated to " + league.getStatus()+ ".");
            }
        }
    }
}
