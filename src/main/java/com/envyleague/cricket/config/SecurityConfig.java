package com.envyleague.cricket.config;

import com.envyleague.cricket.security.AjaxAuthenticationFailureHandler;
import com.envyleague.cricket.security.AjaxAuthenticationSuccessHandler;
import com.envyleague.cricket.security.AjaxLogoutSuccessHandler;
import com.envyleague.cricket.security.Authorities;
import com.envyleague.cricket.security.Http401UnauthorizedEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;

import javax.inject.Inject;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Inject
    private Environment env;

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject
    private RememberMeServices rememberMeServices;

    @Inject
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**")
                .antMatchers("/js/**")
                .antMatchers("/views/**")
                .antMatchers("/css/**")
                .antMatchers("/styles/**")
                .antMatchers("/images/**")
                .antMatchers("/webjars/**")
                .antMatchers("/index.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .key(env.getProperty("envyleague.security.rememberme.key"))
                .and()
                .formLogin()
                .loginProcessingUrl("/user/authentication")
                .successHandler(ajaxAuthenticationSuccessHandler)
                .failureHandler(ajaxAuthenticationFailureHandler)
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .authorizeRequests()
                .antMatchers("/rest/user/register").permitAll()
                .antMatchers("/rest/user/activate").permitAll()
                .antMatchers("/rest/user/authenticate").permitAll()
                .antMatchers("/connect").permitAll()
                .antMatchers("/rest/social/**").authenticated()
                .antMatchers("/rest/cricket/**").hasAuthority(Authorities.USER.name())
                .antMatchers("/rest/league/**").hasAuthority(Authorities.LEAGUE.name())
                .antMatchers("/rest/admin/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/metrics/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/health/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/trace/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/dump/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/shutdown/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/beans/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/configprops/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/info/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/autoconfig/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/env/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/trace/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/api-docs/**").hasAuthority(Authorities.ADMIN.name())
                .antMatchers("/protected/**").authenticated();

    }
}
