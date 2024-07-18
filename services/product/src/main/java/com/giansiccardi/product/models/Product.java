package com.giansiccardi.product.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private Double stock;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;


}
