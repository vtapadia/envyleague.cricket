package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.Authority;
import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.PersistentTokenRepository;
import com.envyleague.cricket.repository.UserRepository;
import com.envyleague.cricket.security.SecurityUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.EnumSet;

@Service
@Transactional
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final int ACTIVATION_KEY_LENGTH = 20;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    public User createUserInformation(String login, String password, String name, String email,
                                      String langKey) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomStringUtils.randomNumeric(ACTIVATION_KEY_LENGTH));
        newUser.setAuthorities(EnumSet.of(Authority.USER));
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        User user = userRepository.getUserByActivationKey(key);
        if (user != null) {
            user.setActivated(true);
            user.setActivationKey(null);
            log.info("Activated user : " + user.getLogin());
            userRepository.save(user);
        }
        return user;
    }

    public void changePassword(String password) {
        User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
        String encryptedPassword = passwordEncoder.encode(password);
        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
        log.debug("Changed password for User: {}", currentUser);
    }

    public User getUserWithAuthorities() {
        User currentUser = null;
        if (SecurityUtils.getCurrentLogin() != null) {
            currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
            if (currentUser != null) {
                currentUser.getAuthorities().size(); // eagerly load the association
            }
        }
        return currentUser;
    }

    public void saveFacebookDetails(String facebookUserId, String facebookUserToken) {
        User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
        currentUser.setFacebookUserId(facebookUserId);
        currentUser.setFacebookAuthToken(facebookUserToken);
        userRepository.save(currentUser);
    }

    public void addIfMissingUserRole(User user, Authority authority) {
        if (!user.getAuthorities().contains(authority)) {
            user.getAuthorities().add(authority);
            userRepository.save(user);
        }
    }
}
