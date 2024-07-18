package com.giansiccardi.notification.kafka.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record Client (String id,

                      String firstname,

                      String lastname,

                      String email){
}
