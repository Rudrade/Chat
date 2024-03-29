package com.example.chat.config;

import com.example.chat.dao.UserDao;
import com.example.chat.web.Flash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by rui on 09/07/2019
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDao userDao;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDao);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin().loginPage("/login").permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
                .logout().permitAll().logoutSuccessUrl("/login");
    }

    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/");
        };
    }

    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("flash", new Flash("Utilizador e/ou password errados", Flash.Status.FAILURE));
            response.sendRedirect("/login");
        };
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
