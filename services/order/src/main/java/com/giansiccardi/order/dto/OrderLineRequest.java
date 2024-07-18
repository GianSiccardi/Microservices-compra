package com.giansiccardi.order.dto;

public record OrderLineRequest(Integer id, Integer orderId,Integer productId, Double quantity) {
}
