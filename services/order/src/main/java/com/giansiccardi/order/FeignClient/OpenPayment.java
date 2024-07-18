package com.giansiccardi.order.FeignClient;

import com.giansiccardi.order.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment", url = "${application.config.payment-url}")
public interface OpenPayment {
    @PostMapping()
    Integer createPayment(@RequestBody PaymentRequest request);


}
