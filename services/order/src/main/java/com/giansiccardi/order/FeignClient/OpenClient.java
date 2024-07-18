package com.giansiccardi.order.FeignClient;

import com.giansiccardi.order.dto.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name="client" ,url="${application.config.client-url}")
public interface OpenClient {

    @GetMapping("/{id}")
    Optional<ClientResponse>findClientById(@PathVariable("id") String id);

}
