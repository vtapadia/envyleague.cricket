package com.envyleague.cricket.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "EL_PERSISTENT_TOKEN")
public class PersistentToken implements Serializable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");

    public PersistentToken() {
    }

    public PersistentToken(PersistentRememberMeToken token, User user) {
        this();
        this.series = token.getSeries();
        this.user = user;
        this.tokenDate = token.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.tokenValue = token.getTokenValue();
    }

    @Id
    private String series;

    @JsonIgnore
    @NotNull
    @Column(name = "token_value", nullable = false)
    private String tokenValue;

    @JsonIgnore
    @Column(name = "token_date")
    private LocalDate tokenDate;

    @JsonIgnore
    @ManyToOne
    private User user;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public LocalDate getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(LocalDate tokenDate) {
        this.tokenDate = tokenDate;
    }

    @JsonGetter
    public String getFormattedTokenDate() {
        return DATE_TIME_FORMATTER.format(this.tokenDate);
    }

    @JsonIgnore
    public PersistentRememberMeToken getPersistentToken() {
        PersistentRememberMeToken token = new PersistentRememberMeToken(
                this.getUser().getLogin(), this.getSeries(), this.getTokenValue(),
                Date.from(this.getTokenDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return token;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersistentToken that = (PersistentToken) o;

        if (!series.equals(that.series)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return series.hashCode();
    }

    @Override
    public String toString() {
        return "PersistentToken{" +
                "series='" + series + '\'' +
                ", tokenValue='" + tokenValue + '\'' +
                ", tokenDate=" + tokenDate +
                "}";
    }
}
