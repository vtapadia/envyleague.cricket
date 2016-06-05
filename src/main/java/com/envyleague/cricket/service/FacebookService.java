package com.envyleague.cricket.service;

import com.envyleague.cricket.domain.User;
import com.envyleague.cricket.repository.UserRepository;
import com.envyleague.cricket.security.SecurityUtils;
import com.envyleague.cricket.web.rest.FacebookClient;
import com.restfb.Connection;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.FacebookClient.DebugTokenInfo;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class FacebookService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${FB_APP_ID:dummy}")
    private String facebookAppId;

    @Value("${FB_APP_SECRET:dummy}")
    private String facebookAppSecret;

    @Inject
    FacebookClient facebookAppClient;

    @Inject
    UserRepository userRepository;

    public User linkFacebookDetails(String code) {
        AccessToken accessToken = facebookAppClient.obtainAccessTokenFromCode(facebookAppId, code);
        DebugTokenInfo debugTokenInfo = facebookAppClient.debugToken(accessToken.getAccessToken());
        FacebookClient userFacebookClient = new FacebookClient(accessToken.getAccessToken(), facebookAppSecret);
        com.restfb.types.User self = userFacebookClient.fetchObject("me", com.restfb.types.User.class);
        log.info("user " + SecurityUtils.getCurrentLogin() + " has linked his facebook details - " + self);

        User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
        currentUser.setFacebookUserId(debugTokenInfo.getUserId());
        currentUser.setFacebookAuthToken(accessToken.getAccessToken());
        currentUser.setName(self.getName());
        userRepository.save(currentUser);
        return currentUser;
    }

    /**
     * Sends a
     * @param user The user to which the notification is to be sent.
     * @param message max 180 characters allowed
     * @return
     */
    public boolean sendNotification(User user, String message) {
        boolean retValue = false;
        if (user.getFacebookUserId() != null && user.getFacebookAuthToken() != null) {
            // TODO Might have to get an extended token so that in the future these calls last the distance
            // facebookAppClient.obtainExtendedAccessToken()
            try {
                facebookAppClient.publish(user.getFacebookUserId() + "/notifications", FacebookType.class,
                        Parameter.with("template", message), //Max 180 characters allowed
                        Parameter.with("ref", "envyleague_notification"));
                retValue = true;
            } catch (Exception e) {
                log.warn("Unable to send notification. ", e);
            }
        }
        return retValue;

    }

    public Set<User> fetchFriends(User user) {
        Set<User> foundFriends = new HashSet<>();
        if (user.getFacebookUserId() != null && user.getFacebookAuthToken() != null) {
            try {
                FacebookClient userFacebookClient = new FacebookClient(user.getFacebookAuthToken(), facebookAppSecret);
                Connection<com.restfb.types.User> facebookFriends =
                        userFacebookClient.fetchConnection("me/friends",com.restfb.types.User.class);
                User friend;
                for (com.restfb.types.User facebookFriend : facebookFriends.getData()) {
                    friend = userRepository.findOneByFacebookUserId(facebookFriend.getId());
                    if (friend != null) {
                        foundFriends.add(friend);
                    }
                }
            } catch (Exception e) {
                log.warn("Unable to find friends.");
            }
        }
        return foundFriends;
    }
}
