package com.giansiccardi.client.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ClienteRequest(String id,
                               String firstname,

                             String lastname ,


                             String email,
                             Address address) {


}
