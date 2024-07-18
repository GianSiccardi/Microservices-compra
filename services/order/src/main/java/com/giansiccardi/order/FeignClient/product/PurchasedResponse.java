package com.giansiccardi.order.FeignClient.product;

import java.math.BigDecimal;

public record PurchasedResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Double quantity

) {
}
