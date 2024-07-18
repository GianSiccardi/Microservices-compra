package com.giansiccardi.notification.repository;

import com.giansiccardi.notification.kafka.payment.PaymentConfirmation;
import com.giansiccardi.notification.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
}
