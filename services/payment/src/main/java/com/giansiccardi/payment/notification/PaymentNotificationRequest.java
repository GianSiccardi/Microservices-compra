package com.giansiccardi.payment.notification;

import com.giansiccardi.payment.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String clientName,
        String clientLastname,
        String clientEmail
) {
}
