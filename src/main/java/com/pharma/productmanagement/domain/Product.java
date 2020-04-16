package com.pharma.productmanagement.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 40,nullable = false)
    private String priceCurrency;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private boolean isActive;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
