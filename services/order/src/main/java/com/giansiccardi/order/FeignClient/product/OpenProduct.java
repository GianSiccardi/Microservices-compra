package com.giansiccardi.order.FeignClient.product;


import com.giansiccardi.order.exception.BusniessException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenProduct {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchasedResponse>purchaseProducts(List<PurchasedRequest> requestBody){
        log.info("Realizando solicitud para comprar productos: {}", requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchasedRequest>>requestEntity= new HttpEntity<>(requestBody , headers);
        log.debug("URL de solicitud: {}", productUrl + "/purchase");
        ParameterizedTypeReference<List<PurchasedResponse>>responseTYpe=
                new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PurchasedResponse>>responseEntity= restTemplate.exchange(
                productUrl+ "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseTYpe
        );
        log.info("Respuesta de compra de productos: {}", responseEntity.getBody());
        if(responseEntity.getStatusCode().isError()){
            throw new BusniessException("Un error ocurrio durante la compra del producto " + responseEntity.getStatusCode());
        }
    return responseEntity.getBody();
    }

}
