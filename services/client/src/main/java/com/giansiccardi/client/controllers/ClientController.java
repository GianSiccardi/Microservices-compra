package com.giansiccardi.client.controllers;

import com.giansiccardi.client.models.ClientResponse;
import com.giansiccardi.client.models.ClienteRequest;
import com.giansiccardi.client.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {


    private final ClientService clientService;


    @PostMapping
    public ResponseEntity<String>createClient(@RequestBody @Valid ClienteRequest request){
        return ResponseEntity.ok(clientService.createClient(request));
    }


    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody @Valid ClienteRequest request){
        clientService.updateClient(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(){

        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean>existById(
            @PathVariable("id") String id){
     return ResponseEntity.ok(clientService.existisById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse>findById(
            @PathVariable("id") String id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable("id") String id){
        clientService.deleteClient(id);
        return ResponseEntity.accepted().body("eliminado");
    }


}
