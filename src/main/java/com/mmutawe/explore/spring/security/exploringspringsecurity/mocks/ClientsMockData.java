package com.mmutawe.explore.spring.security.exploringspringsecurity.mocks;

import com.mmutawe.explore.spring.security.exploringspringsecurity.models.Client;

import java.util.List;
import java.util.UUID;

public class ClientsMockData {

    public static final List<Client> CLIENTS;

    static {
        CLIENTS = List.of(
                new Client(UUID.randomUUID().toString(),
                        "Kuriboh",
                        "YuGiOh"),

                new Client(UUID.randomUUID().toString(),
                        "Meepo",
                        "W33haa"),

                new Client(UUID.randomUUID().toString(),
                        "Pudgy",
                        "Dendi")
                );
    }

    public static Client retrieveClientById(String id){

        return CLIENTS.stream()
                .filter(client -> client.getClientId()
                        .equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Client Id \"" + id + "\" does not exist."));
    }
}
