package com.giansiccardi.order.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusniessException extends RuntimeException{
   private  final String msg;

}
