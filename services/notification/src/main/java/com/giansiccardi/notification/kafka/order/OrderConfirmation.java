package com.giansiccardi.notification.kafka.order;

import com.giansiccardi.notification.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Client client,
        List<Product>products

) {
}
