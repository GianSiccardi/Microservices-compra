package com.giansiccardi.order.kafka;

import com.giansiccardi.order.dto.ClientResponse;
import com.giansiccardi.order.enums.PaymentMethod;
import com.giansiccardi.order.FeignClient.product.PurchasedResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        ClientResponse clientResponse,
        List<PurchasedResponse> products
) {
}
