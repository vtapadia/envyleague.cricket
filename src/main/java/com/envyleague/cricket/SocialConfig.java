package com.envyleague.cricket;

import com.envyleague.cricket.web.rest.FacebookClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${FB_APP_ID}")
    private String facebookAppId;

    @Value("${FB_APP_SECRET}")
    private String facebookAppSecret;

    @Value("${FB_APP_TOKEN}")
    private String facebookAppToken;

    @Bean
    public FacebookClient facebookAppClient() {
        FacebookClient facebookClient = new FacebookClient(facebookAppToken, facebookAppSecret);
        return facebookClient;
    }

}
