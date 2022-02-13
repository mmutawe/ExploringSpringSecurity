package com.mmutawe.explore.spring.security.exploringspringsecurity.configs;

import com.mmutawe.explore.spring.security.exploringspringsecurity.configs.jwt.filters.JwtConfig;
import com.mmutawe.explore.spring.security.exploringspringsecurity.configs.jwt.filters.JwtTokenVerifier;
import com.mmutawe.explore.spring.security.exploringspringsecurity.configs.jwt.filters.JwtUsernamePasswordAuthFilter;
import com.mmutawe.explore.spring.security.exploringspringsecurity.services.auths.ClientSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.AppPermission.CLIENT_WRITE;
import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.ClientRole.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ClientSecurityService clientSecurityService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, ClientSecurityService clientSecurityService, JwtConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.clientSecurityService = clientSecurityService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()   // Only use CSRF protection for any request that could be processed y browser (normal users)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUsernamePasswordAuthFilter.class)
                .authorizeHttpRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/api/**").hasRole(MANAGER.name())
                .antMatchers(POST, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers(PUT, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers(DELETE, "/management/api/**").hasAnyAuthority(CLIENT_WRITE.getPermission())
                .antMatchers( GET,"/management/api/**").hasAnyRole(USER.name(), MANAGER_TRAINEE.name(),MANAGER.name(), ADMIN.name())
                .anyRequest()
                .authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(clientSecurityService);

        return provider;
    }


}
