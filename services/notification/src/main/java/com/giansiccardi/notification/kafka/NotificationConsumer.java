package com.giansiccardi.notification.kafka;

import com.giansiccardi.notification.enums.NotificationType;
import com.giansiccardi.notification.kafka.order.OrderConfirmation;
import com.giansiccardi.notification.kafka.payment.PaymentConfirmation;
import com.giansiccardi.notification.models.Notification;
import com.giansiccardi.notification.repository.NotificationRepository;
import com.giansiccardi.notification.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import static com.giansiccardi.notification.enums.NotificationType.ORDER_CONFIRMATION;
import static com.giansiccardi.notification.enums.NotificationType.PAYMENT_CONFIRMATION;
import static java.lang.String.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;
@KafkaListener(topics = "payment-topic")
   public void consumePaymentSuccesNotifcation(PaymentConfirmation paymentConfirmation) throws MessagingException {
       log.info(format("Consumiento el mensaje desde el payment-topic" , paymentConfirmation));
       repository.save(
               Notification.builder().
               notificationType(PAYMENT_CONFIRMATION).
               notificationDate(LocalDateTime.now()).
               paymentConfirmation(paymentConfirmation)
                       .build()
       );

       // enviar email
    var clientName=paymentConfirmation.clientName() + " " + paymentConfirmation.clientLastname();
    emailService.sentPaymentSuccesEmail(
            paymentConfirmation.clientEmail(),
            clientName,
            paymentConfirmation.amount(),
            paymentConfirmation.orderReference()
    );
   }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderNotifcation(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consumiento el mensaje desde el order-topic" , orderConfirmation));
        repository.save(
                Notification.builder().
                        notificationType(ORDER_CONFIRMATION).
                        notificationDate(LocalDateTime.now()).
                        orderConfirmation(orderConfirmation)
                        .build()
        );

        // enviar email
        var clientName=orderConfirmation.client().firstname() + " "  + orderConfirmation.client().lastname();
        emailService.sentOrderSuccesEmail(
                orderConfirmation.client().email(),
                clientName,
                orderConfirmation.totalAmount(),
               orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }

}
