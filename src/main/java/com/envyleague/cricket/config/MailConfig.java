package com.envyleague.cricket.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        assert env.getProperty("envyleague.mail.password")!=null;
        JavaMailSenderImpl mail = new JavaMailSenderImpl();
        mail.setHost("smtp.gmail.com");
        mail.setPort(587);
        mail.setUsername("envyleague.com@gmail.com");
        mail.setPassword(env.getProperty("envyleague.mail.password"));
        Properties javaProperties = new Properties();
        javaProperties.setProperty("mail.transport.protocol","smtp");
        javaProperties.setProperty("mail.smtp.auth","true");
        javaProperties.setProperty("mail.smtp.starttls.enable","true");
        mail.setJavaMailProperties(javaProperties);
        return mail;
    }
}