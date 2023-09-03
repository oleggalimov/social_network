package org.oleggalimov.examples.social.network.counter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.oleggalimov.examples.social.network.counter.rest.UnreadMessageCountController.UNREAD_MSG_COUNT_URI;

@Configuration
public class SecurityConfig {

    private static final String ALL_CHILD_PATTERN = "/**";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(UNREAD_MSG_COUNT_URI + ALL_CHILD_PATTERN).permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}
