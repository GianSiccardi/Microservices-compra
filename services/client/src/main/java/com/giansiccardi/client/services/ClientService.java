package com.giansiccardi.client.services;

import com.giansiccardi.client.exception.ClientNotFOundExepction;
import com.giansiccardi.client.models.Client;
import com.giansiccardi.client.models.ClientResponse;
import com.giansiccardi.client.models.ClienteRequest;
import com.giansiccardi.client.models.ClientMapper;
import com.giansiccardi.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {




    private final ClientRepository clientRepository;
    private final ClientMapper mapper;



    public String createClient(ClienteRequest request) {
        var client= clientRepository.save(mapper.toClient(request));

        return client.getId();
    }

    public void updateClient(ClienteRequest request) {

        var client=clientRepository.findById(request.id())
                .orElseThrow(()-> new ClientNotFOundExepction(
                        String.format("No se encontro el cliente" , request.id())
                ));
    mergerClient(client,request);
    clientRepository.save(client);

    }

    private void mergerClient(Client client, ClienteRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            client.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            client.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            client.setEmail(request.email());
        }
        if(request.address()!=null){
            client.setAddress(request.address());
        }

    }

    public List<ClientResponse> findAllClients() {

        return clientRepository.findAll()
                .stream()
                .map(mapper::fromClient)
                .collect(Collectors.toList());
    }

    public Boolean existisById(String id) {

        return clientRepository.findById(id).isPresent();
    }

    public ClientResponse findById(String id) {
        return clientRepository.findById(id).
                map(mapper::fromClient)
                .orElseThrow(()->new ClientNotFOundExepction(String.format("No se encontro el cliente ",id)));
    }

    public void deleteClient(String id) {
    clientRepository.deleteById(id);

    }
}
