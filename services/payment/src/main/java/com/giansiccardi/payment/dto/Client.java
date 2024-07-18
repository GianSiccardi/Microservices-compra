package com.giansiccardi.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Client(String id,
                     @NotNull(message = "el nombre es necesario")
                     String firstname,
                     @NotNull(message = "el apellido es necesario")
                     String lastname,
                     @NotNull(message = "el email es necesario")
                     @Email(message = "debe ser formato email")
                     String email) {
}
