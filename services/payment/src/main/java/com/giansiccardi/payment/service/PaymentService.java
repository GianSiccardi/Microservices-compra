package com.giansiccardi.payment.service;

import com.giansiccardi.payment.dto.PaymentRequest;
import com.giansiccardi.payment.models.Payment;
import com.giansiccardi.payment.notification.NotificationProducer;
import com.giansiccardi.payment.notification.PaymentNotificationRequest;
import com.giansiccardi.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {


    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;


    public Integer createPayment(PaymentRequest request) {

        log.info("Recibida solicitud de pago en el servicio: {}", request);
      try{
        var payment=repository.save(mapper.toPayment(request));
          log.info("Pago persistido en la base de datos: {}", payment);
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.client().firstname(),
                        request.client().lastname(),
                        request.client().email()
                )
        );

          log.info("Notificación de pago enviada con éxito.");

        return payment.getId();

    } catch (KafkaException | SerializationException | TimeoutException | AuthorizationException | IllegalStateException e) {
        // Manejar errores específicos de Kafka
        log.error("Error al enviar notificación a Kafka: {}", e.getMessage());
        throw e; // O devuelve una respuesta específica
    } catch (Exception e) {
        // Manejar otras excepciones
        log.error("Error inesperado al crear pago: {}", e.getMessage());
        throw new RuntimeException("Error inesperado al crear pago", e);
    }

    }

    public List<Payment> findAll() {
        return repository.findAll();
    }
}
