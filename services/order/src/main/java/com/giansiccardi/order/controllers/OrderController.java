package com.giansiccardi.order.controllers;

import com.giansiccardi.order.dto.OrderRequest;
import com.giansiccardi.order.dto.OrderResponse;
import com.giansiccardi.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<Integer>createOrder(
            @RequestBody @Valid OrderRequest request
    ) throws SocketTimeoutException, ConnectException {
        log.info("Recibida solicitud para crear orden en el controlador: {}", request);
        log.info("Lista de productos en la solicitud en el controlador: {}", request.products());
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>>findAll(){
        log.info("Recibida solicitud para el controlador: {}");
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse>findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.findById(id));
    }
}
