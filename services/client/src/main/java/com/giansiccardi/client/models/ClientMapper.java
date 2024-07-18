package com.giansiccardi.client.models;


import org.springframework.stereotype.Service;

@Service
public class ClientMapper {


    public Client toClient(ClienteRequest request){

        if(request ==null){
            return null;
        }
        return Client.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public ClientResponse fromClient(Client client) {
    return new ClientResponse(
            client.getId(),
            client.getFirstname(),
            client.getLastname(),
            client.getEmail(),
            client.getAddress()
    );

    }
}
