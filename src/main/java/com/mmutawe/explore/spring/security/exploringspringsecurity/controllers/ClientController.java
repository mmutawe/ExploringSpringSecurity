package com.mmutawe.explore.spring.security.exploringspringsecurity.controllers;

import com.mmutawe.explore.spring.security.exploringspringsecurity.mocks.ClientsMockData;
import com.mmutawe.explore.spring.security.exploringspringsecurity.models.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    @GetMapping(path = "/{clientId}")
    public Client getClient(@PathVariable String clientId) {
        Client client = ClientsMockData.retrieveClientById(clientId);
        return client;
    }
}
