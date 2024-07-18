package com.giansiccardi.notification.enums;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html","EL pago fue procesado exitosamente"),
    ORDER_CONFIRMATION("order-confirmation.html","La orden se realizo exitosamente");

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    @Getter
    private final String template;

    @Getter
    private final String subject;

}
