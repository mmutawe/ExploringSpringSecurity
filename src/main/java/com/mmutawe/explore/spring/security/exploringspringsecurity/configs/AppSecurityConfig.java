package com.mmutawe.explore.spring.security.exploringspringsecurity.configs;

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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.AppPermission.CLIENT_WRITE;
import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.ClientRole.*;
import static org.springframework.http.HttpMethod.*;

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
        http
                .csrf().disable()   // Only use CSRF protection for any request that could be processed y browser (normal users)
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 'withHttpOnlyFalse' means that the cookie will be accessible to client side scripts (ex: Javascript)
//                .and()
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").hasRole(MANAGER.name())
                .antMatchers(POST, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers(PUT, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers(DELETE, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers( GET,"/management/api/**").hasAnyRole(USER.name(), MANAGER_TRAINEE.name(),MANAGER.name(), ADMIN.name())
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
//                .roles(MANAGER.name())
                .authorities(MANAGER.getGrantedAuthorities())
                .build();

        UserDetails userLina = User.builder()
                .username("Lina")
                .password(passwordEncoder.encode("pass"))
//                .roles(MANAGER_TRAINEE.name()) // ROLE_MANAGER_TRAINEE
                .authorities(MANAGER_TRAINEE.getGrantedAuthorities())
                .build();

        UserDetails userMarci = User.builder()
                .username("Marci")
                .password(passwordEncoder.encode("pass"))
//                .roles(USER.name())
                .authorities(USER.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                userInvoker,
                userLina,
                userMarci
        );
    }
}
