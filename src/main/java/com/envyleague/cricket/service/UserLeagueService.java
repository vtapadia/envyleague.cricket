package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.Status;
import com.envyleague.cricket.domain.UserLeague;
import com.envyleague.cricket.repository.UserLeagueRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Service
@Transactional
public class UserLeagueService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    EntityManager entityManager;

    @Inject
    MailService mailService;

    @Inject
    FacebookService facebookService;

    @Inject
    UserLeagueRepository userLeagueRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(UserLeague userLeague, Status status) {
        if (userLeague.getStatus() != status) {
            log.info("Updating {} for {} for {}", status, userLeague.getUser().getLogin(), userLeague.getLeague().getName());
            userLeague.setStatus(status);
            userLeagueRepository.save(userLeague);
            if (status == Status.ACTIVE) {
                StringBuilder sb = new StringBuilder();
                sb.append("<p>Dear " + userLeague.getUser().getLogin() + ",");
                sb.append("<p>You can now update your scores for '" + userLeague.getLeague().getName() + "' league. <br> ");
                if (userLeague.getLeague().getFee() > 0) {
                    sb.append("<p>For all financial transactions, please get in touch with League Owner:"
                            + userLeague.getLeague().getOwner().getLogin() + "(" + userLeague.getLeague().getOwner().getEmail() + "). <br> ");
                }
                sb.append("<p>Enjoy and Best of Luck for the game and keep promoting www.envyleague.com .<br>");
                sb.append("<p>Do get in touch with us in case of any concerns.<br>");
                sb.append("<p>Thanks<br>Support");
                mailService.sendEmail(userLeague.getUser().getEmail(),
                        "Welcome to " + userLeague.getLeague().getName() + " league",
                        sb.toString());
                if (StringUtils.isNotBlank(userLeague.getUser().getFacebookUserId())) {
                    log.info("Sending facebook notifications.");
                    facebookService.sendNotification(userLeague.getUser(), userLeague.getLeague().getName()+" League joining request approved.");
                }

            }
        }
    }
}
