package com.giansiccardi.order.FeignClient.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchasedRequest(
        @NotNull(message = "El producto es obligatorio")
        Integer productId,
        @Positive(message = "La cantidad es obligatoria")
        Double quantity
) {
}
