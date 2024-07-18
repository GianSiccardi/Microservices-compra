package com.giansiccardi.client.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ClientResponse (String id,
                             @NotNull(message = "El nombre es obligatorio")
                             String firstname,
                             @NotNull(message = "El apellido es obligatorio")
                             String lastname ,
                             @NotNull(message = "El email es obligatorio")
                             @Email(message = "El email no es valido")
                             String email,
                             Address address){
}
