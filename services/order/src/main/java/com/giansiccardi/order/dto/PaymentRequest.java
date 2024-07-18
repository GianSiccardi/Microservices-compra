package com.giansiccardi.order.dto;

import com.giansiccardi.order.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
                             BigDecimal amount,

                             PaymentMethod paymentMethod,
                             Integer orderId,
                             String orderReference,
                             ClientResponse client) {
}
