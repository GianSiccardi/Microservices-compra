package com.giansiccardi.client.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientNotFOundExepction extends RuntimeException {

    private final String msg;


}
