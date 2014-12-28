package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.service.FacebookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Inject
    private FacebookService facebookService;

    private static final String WELCOME_MSG = "Thanks and Welcome to the league you can call yours.";

    /**
     * Called when receives a response after facebook authentication.
     * @param code
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> handleFacebookResponse(@NotNull @RequestParam(value = "code") String code,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        User user = facebookService.linkFacebookDetails(code);
        facebookService.sendNotification(user, WELCOME_MSG);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://www.envyleague.com/");
        return new ResponseEntity<byte []>(null,headers,HttpStatus.FOUND);
    }

    /**
     * TODO Need to see its usefulness
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateUser(HttpServletRequest request,
                                          HttpServletResponse response) {
        //TODO Need to work here
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
