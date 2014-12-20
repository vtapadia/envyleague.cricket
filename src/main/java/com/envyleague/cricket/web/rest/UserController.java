package com.envyleague.cricket.web.rest;

import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.UserRepository;
import com.envyleague.cricket.service.MailService;
import com.envyleague.cricket.service.UserService;
import com.envyleague.cricket.web.dto.UserDTO;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    MailService mailService;

    @RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO, HttpServletRequest request,
                                             HttpServletResponse response) {
        if (userRepository.findOne(userDTO.getLogin()) != null) {
            return new ResponseEntity<String>("login already in use", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findOneByEmail(userDTO.getEmail()) != null) {
            return new ResponseEntity<String>("e-mail address already in use", HttpStatus.BAD_REQUEST);
        }
        log.info("Registering new user " + userDTO);

        User user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(),
                userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
                userDTO.getLangKey());
        //final Locale locale = Locale.forLanguageTag(user.getLangKey());
        mailService.sendActivationMail(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/activate",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activateUser(String key) {
        User user = userService.activateRegistration(key);
        if (user != null) {
            return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
