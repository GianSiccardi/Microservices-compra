package com.giansiccardi.order.service;

import com.giansiccardi.order.dto.OrderLineRequest;
import com.giansiccardi.order.dto.OrderLineResponse;
import com.giansiccardi.order.models.Order;
import com.giansiccardi.order.models.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
    return OrderLine.builder()
                    .id(orderLineRequest.id()).
        quantity(orderLineRequest.quantity()).
            order(Order.builder()
                    .id(orderLineRequest.orderId())
                    .build()).
            prodcutId(orderLineRequest.productId()).
             build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(),orderLine.getQuantity());
    }
}
