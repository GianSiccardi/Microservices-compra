package com.giansiccardi.order.dto;

public record ClientResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
