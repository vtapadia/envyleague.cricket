package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.User;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject, String content) {
        sendEmail(to, subject, content, false, true);
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom("support@envyleague.com");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'!", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    public void sendActivationMail(User user) {
        sendEmail(user.getEmail(), "Activation mail for www.envyleague.com",
                "<p>Dear " + user.getLogin() + ",<p>Please click the below link for activating user account. <br> " +
                        "www.envyleague.com/#/activate?key="+user.getActivationKey()+"<br>" +
                        "<p>Thanks<br>Nidhi and Varesh");
    }
}
