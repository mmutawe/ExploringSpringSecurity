package com.mmutawe.explore.spring.security.exploringspringsecurity.services.auths;

import com.google.common.collect.Lists;
import com.mmutawe.explore.spring.security.exploringspringsecurity.models.auths.ClientSecurityDetail;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.ClientRole.*;

@Repository("MockClientDetailRepo")
public class ClientSecurityDaoImpl implements ClientSecurityDao{

    private final PasswordEncoder passwordEncoder;

    public ClientSecurityDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ClientSecurityDetail> selectClientSecurityDetailByUsername(String username) {
        return getClientSecurityDetail().stream()
                .filter(clientDetail -> username.equals((clientDetail.getUsername())))
                .findAny();
    }

    private List<ClientSecurityDetail> getClientSecurityDetail(){
        List<ClientSecurityDetail> list = Lists.newArrayList(
                new ClientSecurityDetail(
                        MANAGER.getGrantedAuthorities(),
                        passwordEncoder.encode("pass"),
                        "Invoker",
                        true,
                        true,
                        true,
                        true
                ),
                new ClientSecurityDetail(
                        MANAGER_TRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("pass"),
                        "Lina",
                        true,
                        true,
                        true,
                        true
                ),
                new ClientSecurityDetail(
                        USER.getGrantedAuthorities(),
                        passwordEncoder.encode("pass"),
                        "Marci",
                        true,
                        true,
                        true,
                        true
                )
        );
        return list;
    }
}
