package com.giansiccardi.order.controllers;

import com.giansiccardi.order.dto.OrderLineResponse;
import com.giansiccardi.order.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-lines")
@RequiredArgsConstructor
public class OrderLineController {

private final OrderLineService orderLineService;

@GetMapping("/order/{id}")
    public ResponseEntity<List<OrderLineResponse>>findByOrderId(@PathVariable("id") Integer id){
    return ResponseEntity.ok(orderLineService.findAllByOrderId(id));
}


}
