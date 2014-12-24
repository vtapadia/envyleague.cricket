package com.envyleague.cricket.web.rest;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookResponseContentException;

public class FacebookClient extends DefaultFacebookClient {
    public FacebookClient(String appToken, String appSecret) {
        super(appToken, appSecret);
    }

    public AccessToken obtainAccessTokenFromCode(String appId, String appSecret, String code) {
        this.verifyParameterPresence("code", code);
        String response = this.makeRequest("oauth/access_token", new Parameter[]{
                Parameter.with("client_id", appId),
                Parameter.with("redirect_uri", "http://www.envyleague.com/rest/social/facebook/"),
                Parameter.with("client_secret", appSecret),
                Parameter.with("code", code)});

        try {
            return AccessToken.fromQueryString(response);
        } catch (Throwable t) {
            throw new FacebookResponseContentException("Unable to extract access token from response.", t);
        }
    }

    public DebugTokenInfo debugToken(String inputToken) {
        return super.debugToken(inputToken);
    }
}
