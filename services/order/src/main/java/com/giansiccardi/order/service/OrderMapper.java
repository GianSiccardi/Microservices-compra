package com.giansiccardi.order.service;

import com.giansiccardi.order.dto.OrderRequest;
import com.giansiccardi.order.dto.OrderResponse;
import com.giansiccardi.order.models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request) {
    return Order.builder().
            id(request.id()).
            clientId(request.clientId()).
            reference(request.reference()).
            amount(request.amount()).
            paymentMethod(request.paymentMethod())
            .build();

    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getAmount(),
                order.getPaymentMethod(),
                order.getClientId()
        );
    }
}
