package com.envyleague.cricket.web.dto;

import com.envyleague.cricket.domain.Authority;
import com.envyleague.cricket.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String langKey;

    private String facebookUserId;

    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.langKey = user.getLangKey();
        this.roles = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
        this.email = user.getEmail();
    }

    public UserDTO(String login, String password, String firstName, String lastName, String email, String langKey,
                   List<String> roles) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.langKey = langKey;
        this.roles = roles;
    }

    public UserDTO(String login, String password, String firstName, String lastName, String email, String langKey,
                   String facebookUserId,
                   List<String> roles) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDTO{");
        sb.append("login='").append(login).append('\'');
        if(password != null) {
            sb.append(", password='").append(password.length()).append('\'');
        }
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
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
