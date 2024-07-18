package com.giansiccardi.payment.controllers;

import com.giansiccardi.payment.dto.PaymentRequest;
import com.giansiccardi.payment.models.Payment;
import com.giansiccardi.payment.service.PaymentService;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody @Valid PaymentRequest request) {
        try {
            log.info("Recibida solicitud de pago: {}", request);
            Integer paymentId = service.createPayment(request);
            return ResponseEntity.ok(paymentId);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation exception occurred: {}", e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation: " + e.getMessage());
        } catch (FeignException e) {

            e.printStackTrace();
            log.error("Feign client exception occurredd: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("External service error: " + e.getMessage());
        }catch (KafkaException e) {
            // Manejo específico para errores de Kafka
            e.printStackTrace(); // Imprime la traza de la excepción para diagnóstico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kafka error: " + e.getMessage());
        } catch (Exception e) {


            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Payment>>findAll(){
        return ResponseEntity.ok(service.findAll());
    }
}
