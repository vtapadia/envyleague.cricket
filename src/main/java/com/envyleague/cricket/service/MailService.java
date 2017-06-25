package com.envyleague.cricket.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

public interface MailService {
    @Async
    void sendEmail(String to, String subject, String content);
}
