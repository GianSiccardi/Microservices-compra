package com.giansiccardi.notification.kafka.payment;

import com.giansiccardi.notification.enums.PaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,


        String clientName,

        String clientLastname,

        String clientEmail)
 {
}
