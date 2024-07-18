package com.giansiccardi.order.dto;


import com.giansiccardi.order.enums.PaymentMethod;
import com.giansiccardi.order.FeignClient.product.PurchasedRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "debe ser positivio")
        BigDecimal amount,
        @NotNull(message = "debe tener un metodo de pago")
        PaymentMethod paymentMethod,
        @NotNull(message = "El id del cliente tiene que estar")
        @NotBlank(message = "El id del cliente tiene que estar")
        @NotEmpty(message = "El id del cliente tiene que estar")
        String clientId,

       // @NotNull(message ="debes comprar al menos un producto" )
        List<PurchasedRequest> products

) {
}
