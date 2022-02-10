package com.mmutawe.explore.spring.security.exploringspringsecurity.controllers;

import com.mmutawe.explore.spring.security.exploringspringsecurity.mocks.MockDataService;
import com.mmutawe.explore.spring.security.exploringspringsecurity.models.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/client")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ClientManagementController {

    Logger logger = LoggerFactory.getLogger(ClientManagementController.class);

    // hasRole('ROLE_')
    // hasAnyRole('ROLE_')
    // hasAuthority('permission')
    // hasAnyAuthority()'permission'

    @GetMapping
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<Client> getAllClients() {
        logger.info("getAllClients method was called");
        return MockDataService.retrieveAllClients();
    }

    @PostMapping
    public void registerNewClient(@RequestBody Client client) {
        logger.info("registerNewClient method was called");
        MockDataService.registerClient(client);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable String clientId) {
        logger.info("deleteClient method was called");
        MockDataService.deleteClient(clientId);
    }

    @PutMapping("/{clientId}")
    public void updateClient(@PathVariable String clientId,
                             @RequestBody Client client) {
        logger.info("updateClient method was called");
        MockDataService.updateClient(clientId, client);
    }

}
