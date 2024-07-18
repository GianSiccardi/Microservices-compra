package com.giansiccardi.order.models;

import com.giansiccardi.order.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="client_order")
public class Order {

    @Id
    @GeneratedValue
    private Integer id;

    private String reference;

    private BigDecimal amount;

    @Enumerated(STRING)
    private PaymentMethod paymentMethod;

    private String clientId;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLineList;

    @CreatedDate
    @Column(updatable = false , nullable = false)
    private LocalDateTime createdDate;
    @CreatedDate
    @Column(insertable = false )
    private LocalDateTime lastModifiedDate;

}
