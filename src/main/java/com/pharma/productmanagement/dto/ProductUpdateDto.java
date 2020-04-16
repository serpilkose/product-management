package com.pharma.productmanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductUpdateDto {

    private String name;

    private BigDecimal price;

    private String priceCurrency;
}
