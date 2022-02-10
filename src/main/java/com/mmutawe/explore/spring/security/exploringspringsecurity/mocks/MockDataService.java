package com.mmutawe.explore.spring.security.exploringspringsecurity.mocks;

import com.mmutawe.explore.spring.security.exploringspringsecurity.models.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockDataService {

    public static final List<Client> CLIENTS = new ArrayList<>();

    static {
        CLIENTS.add(new Client(UUID.randomUUID().toString(),
                "Kuriboh",
                "YuGiOh"));

        CLIENTS.add(new Client(UUID.randomUUID().toString(),
                "Meepo",
                "W33haa"));

        CLIENTS.add(new Client(UUID.randomUUID().toString(),
                "Pudgy",
                "Dendi"));
    }

    public static Client retrieveClientById(String id) {

        return CLIENTS.stream()
                .filter(client -> client.getClientId()
                        .equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Client Id \"" + id + "\" does not exist."));
    }

    public static List<Client> retrieveAllClients() {
        return CLIENTS;
    }

    public static void registerClient(Client client) {
        client.setClientId(UUID.randomUUID().toString());
        CLIENTS.add(client);

        // print all clients
        CLIENTS.forEach(System.out::println);
    }

    public static void deleteClient(String id) {
        CLIENTS.removeIf(client -> client.getClientId().equals(id));

        // print all clients
        CLIENTS.forEach(System.out::println);
    }

    public static void updateClient(String id, Client client) {
        boolean isClientExist = CLIENTS.stream()
                .anyMatch(c -> c.getClientId().equals(id));

        if (!isClientExist) {
            return;
        }

        CLIENTS.removeIf(c -> c.getClientId().equals(id));
        client.setClientId(id);
        CLIENTS.add(client);

        // print all clients
        CLIENTS.forEach(System.out::println);
    }


}
