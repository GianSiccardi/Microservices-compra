package com.giansiccardi.payment.dto;

import com.giansiccardi.payment.enums.PaymentMethod;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Client client
) {
}