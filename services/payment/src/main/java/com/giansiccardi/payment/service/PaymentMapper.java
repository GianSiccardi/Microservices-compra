package com.giansiccardi.payment.service;

import com.giansiccardi.payment.dto.PaymentRequest;
import com.giansiccardi.payment.models.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        log.info("Mapeando PaymentRequest a Payment: {}", request);
        Payment payment = Payment.builder()
                .id(request.id())
                .orderId(request.orderId())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();

        log.info("Entidad Payment mapeada exitosamente: {}", payment);

        return payment;
    }
}