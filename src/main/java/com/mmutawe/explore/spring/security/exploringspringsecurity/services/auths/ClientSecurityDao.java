package com.mmutawe.explore.spring.security.exploringspringsecurity.services.auths;

import com.mmutawe.explore.spring.security.exploringspringsecurity.models.auths.ClientSecurityDetail;

import java.util.Optional;

public interface ClientSecurityDao {
    Optional<ClientSecurityDetail> selectClientSecurityDetailByUsername(String username);
}
