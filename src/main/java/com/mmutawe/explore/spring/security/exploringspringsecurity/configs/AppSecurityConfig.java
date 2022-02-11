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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

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
        // *** Form Based Auth ***
        // - to switch from basic-authentication to form based authentication,
        //   we use formLogin() instead of httpBasic().
        // - Form Based Auth will provide the app users a session-token each time the user logged in,
        //   the token will stay active for 30 min. then another login request must called.
        // - rememberMe() method allow token based remember me authentication. Upon authenticating
        //   if the HTTP parameter named "remember-me" exists, then the user will be remembered
        //   even after their HttpSession expires. (default 2 weeks)
        // - When logged out, Spring security clear session ID/token as well as remember-me cookies.
        // - if CSRF protection is enabled (default), then the request must be a POST. However, if
        //   CSRF protection is disabled, then any HTTP method is allowed. (POST is recommended)
        http
                .csrf().disable()   // Only use CSRF protection for any request that could be processed y browser (normal users)
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 'withHttpOnlyFalse' means that the cookie will be accessible to client side scripts (ex: Javascript)
//                .and()
                .authorizeHttpRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/api/**").hasRole(MANAGER.name())
                .antMatchers(POST, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers(PUT, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers(DELETE, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers( GET,"/management/api/**").hasAnyRole(USER.name(), MANAGER_TRAINEE.name(),MANAGER.name(), ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic(); // --> basic-auth
                .formLogin() // --> form based auth
                    .loginPage("/login")
                    .defaultSuccessUrl("/welcome",true)
//                    .usernameParameter("username")
//                    .passwordParameter("password")
                    .and()
                    .rememberMe() // default 2 weeks
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                        .key("md5 hash passcode something something")
//                        .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", GET.name()))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
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
