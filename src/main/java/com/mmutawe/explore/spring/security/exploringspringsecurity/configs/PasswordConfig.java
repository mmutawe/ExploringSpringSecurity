package com.mmutawe.explore.spring.security.exploringspringsecurity.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //strength – the log rounds to use, between 4 and 31
        return new BCryptPasswordEncoder(10);
    }
}
