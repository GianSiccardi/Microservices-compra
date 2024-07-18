package com.giansiccardi.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(Integer id,
         @NotNull(message = "El producto es necesario")
         String name,
                             @NotNull(message = "La descripcion es necesaria")

                             String description,
                             @NotNull(message = "El stock es necesario")
                             @Positive(message = "Tiene que ser positivo")
                             Double stock,
                             @NotNull(message = "El precio es necesario")
                             @Positive(message = "Debe ser un precio positivo")
         BigDecimal price,
         @NotNull(message = "La categoria id es necesaria")
         Integer categoryId                    ) {
}
