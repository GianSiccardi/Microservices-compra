package com.giansiccardi.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductResponse(Integer id,

                              String name,


                              String description,


                              Double stock,


                              BigDecimal price,

                              Integer categoryId,
                              String categoryName,
                              String categoryDescription) {
}
