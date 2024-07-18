package com.giansiccardi.order.service;

import com.giansiccardi.order.FeignClient.OpenClient;
import com.giansiccardi.order.FeignClient.OpenPayment;
import com.giansiccardi.order.FeignClient.product.OpenProduct;
import com.giansiccardi.order.dto.*;
import com.giansiccardi.order.exception.BusniessException;
import com.giansiccardi.order.kafka.OrderConfirmation;
import com.giansiccardi.order.kafka.OrderProducer;
import com.giansiccardi.order.FeignClient.product.PurchasedRequest;
import com.giansiccardi.order.repository.OrderRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OpenClient openClient;
    private final OpenProduct openProduct;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final OpenPayment openPayment;


    public Integer createOrder(OrderRequest request) throws ConnectException, SocketTimeoutException {
        log.info("Recibida solicitud para crear orden en el servicio: {}", request);
        log.info("Lista de productos recibida en el service: {}", request.products());


        try {
            // Verificar el cliente usando OpenFeign

            var client = openClient.findClientById(request.clientId())
                    .orElseThrow(() -> new BusniessException("No se encontró el cliente"));


            log.info("Cliente encontrado: {}", client);

            var purchasedProducts = openProduct.purchaseProducts(request.products());
            log.info("Productos comprados: {}", purchasedProducts);


            var order = orderRepository.save(orderMapper.toOrder(request));
            log.info("Orden persistida: {}", order);

            if (request.products() == null) {
                log.warn("La lista de productos está llegando como null");
            }

            for (PurchasedRequest purchasedRequest : request.products()) {
                log.info("Procesando producto: productId={}, quantity={}", purchasedRequest.productId(), purchasedRequest.quantity());
                orderLineService.saveOrderLine(
                        new OrderLineRequest(
                                null,
                                order.getId(),
                                purchasedRequest.productId(),
                                purchasedRequest.quantity()
                        )
                );
                log.info("Línea de orden persistida para productId {}: cantidad {}", purchasedRequest.productId(), purchasedRequest.quantity());
            }


            var paymentRequest = new PaymentRequest(
                    request.amount(),
                    request.paymentMethod(),
                    order.getId(),
                    order.getReference(),
                    client
            );

            log.info("INFORMACION DE PAYMENTREQUEST: {}", paymentRequest);
            log.info("INFORMACION DE CLIENTE DEL PAYMENTE REQUET: {}", paymentRequest.client());
            openPayment.createPayment(paymentRequest);

            // Enviar confirmación de orden por Kafka
            orderProducer.sendOrderConfirmation(new OrderConfirmation(
                    request.reference(),
                    request.amount(),
                    request.paymentMethod(),
                    client,
                    purchasedProducts
            ));

            return order.getId();



        } catch (FeignException fe) {
            log.error("Error al comunicar con el servicio de pagos: {}", fe.getMessage(), fe);
            throw new RuntimeException("Error al procesar el pago", fe);
        } catch (ResourceAccessException rae) {
            log.error("Error de acceso al recurso: {}", rae.getMessage(), rae);
            throw new RuntimeException("Error de acceso al recurso", rae);
        } catch (Exception e) {
            log.error("Error inesperado al crear la orden: {}", e.getMessage(), e);
            throw new RuntimeException("Error inesperado al crear la orden", e);
        }
    }


    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {

        return  orderRepository.findById(id)
                .map(orderMapper::fromOrder)
                .orElseThrow(()-> new EntityNotFoundException(String.format("No se encontro la orden" ,id)));
    }
}
