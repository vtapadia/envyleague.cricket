package com.envyleague.cricket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MailServiceDev implements MailService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Async
    public void sendEmail(String to, String subject, String content) {
        log.info("DEV sent mail to {} with subject {} and body {}", to, subject, content);
    }
}
