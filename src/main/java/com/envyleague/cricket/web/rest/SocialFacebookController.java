package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.service.UserService;
import com.restfb.Connection;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/rest/social/facebook")
public class SocialFacebookController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${FB_APP_ID}")
    private String facebookAppId;

    @Value("${FB_APP_SECRET}")
    private String facebookAppSecret;

    @Value("${FB_APP_TOKEN}")
    private String facebookAppToken;

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> handleFacebookResponse(@NotNull @RequestParam(value = "code") String code,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        FacebookClient facebookClient = new FacebookClient(facebookAppToken, facebookAppSecret);
        AccessToken accessToken = facebookClient.obtainAccessTokenFromCode(facebookAppId, facebookAppSecret, code);
        DebugTokenInfo debugTokenInfo = facebookClient.debugToken(accessToken.getAccessToken());
        System.out.println(debugTokenInfo);
        FacebookClient userFacebookClient = new FacebookClient(accessToken.getAccessToken(), facebookAppSecret);
        User self = userFacebookClient.fetchObject("me", User.class);
        System.out.println("self=" + self);
        Connection<User> users = userFacebookClient.fetchConnection("me/friends", User.class);
        System.out.println("users=" + users);
        userService.saveFacebookDetails(debugTokenInfo.getUserId(), accessToken.getAccessToken());
        FacebookType fb = facebookClient.publish(debugTokenInfo.getUserId() + "/notifications", FacebookType.class, Parameter.with("template", "Testing notification from envyleague"), Parameter.with("ref", "eleague_notification"));
        System.out.println(fb);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://www.envyleague.com/");

        return new ResponseEntity<byte []>(null,headers,HttpStatus.FOUND);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateUser(HttpServletRequest request,
                                          HttpServletResponse response) {
        //TODO Need to work here
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
