package com.giansiccardi.product.dto;

import jakarta.validation.constraints.NotNull;

public record  ProductPurchaseRequest (
        @NotNull
        Integer productId,
        @NotNull
        Double quantity
){}
