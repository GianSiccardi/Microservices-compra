package com.giansiccardi.product.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productoId,
        String name,
        String description,
        Double quantity,
        BigDecimal price

) {
}
