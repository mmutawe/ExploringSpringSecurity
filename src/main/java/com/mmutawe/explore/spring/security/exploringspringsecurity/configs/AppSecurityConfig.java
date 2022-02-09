package com.mmutawe.explore.spring.security.exploringspringsecurity.configs;

import com.mmutawe.explore.spring.security.exploringspringsecurity.enums.ClientRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.ClientRole.MANAGER;
import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.ClientRole.USER;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // *** configure Basic-Auth ***
        // - a pop-up login form will be generated when try to retrieve the data from a browser.
        // - the username and password will be combined and encoded using base64.
        // - The encrypted data will then be saved within the 'Authorization' field in the header
        // - Cons --> we can NOT logout (we will keep sending the username and password for each request)
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").hasRole(MANAGER.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }

    // Responsible for determine how we retrieve users from the database
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        UserDetails userInvoker = User.builder()
                .username("Invoker")
                .password(passwordEncoder.encode("pass"))
                .roles(MANAGER.name())
                .build();

        UserDetails userMarci = User.builder()
                .username("Marci")
                .password(passwordEncoder.encode("pass"))
                .roles(USER.name())
                .build();

        return new InMemoryUserDetailsManager(
                userInvoker,
                userMarci
        );
    }
}
