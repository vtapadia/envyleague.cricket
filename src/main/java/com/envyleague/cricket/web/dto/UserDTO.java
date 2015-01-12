package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.Authority;
import com.envyleague.cricket.domain.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    private String login;

    private String password;

    private String name;

    private String email;

    private String langKey;

    private String facebookUserId;

    private Set<Authority> roles;

    private int points;
    private Integer rank;
    private int prize;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.name = user.getName();
        this.langKey = user.getLangKey();
        this.roles = user.getAuthorities();
        this.facebookUserId = user.getFacebookUserId();
        this.email = user.getEmail();
    }

    public UserDTO(String login, String password, String name, String email, String langKey,
                   Set<Authority> roles) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.langKey = langKey;
        this.roles = roles;
    }

    public UserDTO(String login, String password, String name, String email, String langKey,
                   String facebookUserId,
                   Set<Authority> roles) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.langKey = langKey;
        this.facebookUserId = facebookUserId;
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public Set<Authority> getRoles() {
        return roles;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDTO{");
        sb.append("login='").append(login).append('\'');
        if(password != null) {
            sb.append(", password='").append(password.length()).append('\'');
        }
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", langKey='").append(langKey).append('\'');
        if (facebookUserId != null) {
            sb.append(", facebookUserId='").append(facebookUserId).append('\'');
        }
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
