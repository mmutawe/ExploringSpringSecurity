package com.mmutawe.explore.spring.security.exploringspringsecurity.services.auths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientSecurityService implements UserDetailsService {

    private final ClientSecurityDao clientSecurityDao;

    @Autowired
    public ClientSecurityService(@Qualifier("MockClientDetailRepo") ClientSecurityDao clientSecurityDao) {
        this.clientSecurityDao = clientSecurityDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientSecurityDao
                .selectClientSecurityDetailByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("Client with a name \'%s\' not found.", username))
                );
    }
}
