package com.tomshidi.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author tangshili
 * @since 2023/4/12 16:47
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    protected void filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
                .anyRequest()
                .permitAll()
//                .authenticated()
                .and()
                .headers()
                .frameOptions()
                .disable();
    }
}
