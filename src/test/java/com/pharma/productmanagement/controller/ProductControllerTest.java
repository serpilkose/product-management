package com.pharma.productmanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.productmanagement.domain.Product;
import com.pharma.productmanagement.dto.ProductUpdateDto;
import com.pharma.productmanagement.service.ProductService;
import com.pharma.productmanagement.service.exceptions.ProductNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;
    @MockBean
    private Clock clock;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2020-04-12T01:00:00Z"), ZoneId.of("UTC"));

    @BeforeEach
    private void setUp() {
        when(clock.instant()).thenReturn(FIXED_CLOCK.instant());
        when(clock.getZone()).thenReturn(FIXED_CLOCK.getZone());
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        //Given
        Product product = Product.builder()
                .name("aspirin")
                .price(new BigDecimal("1.2"))
                .priceCurrency("GBP")
                .build();
        when(productService.add(any(Product.class))).thenReturn(product);
        //when
        ResultActions restCallResponse = mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)));
        //then
        restCallResponse
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(product)));
        verify(productService, times(1)).add(productArgumentCaptor.capture());
        Assertions.assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("aspirin");
    }

    @Test
    public void shouldListAllProducts() throws Exception {
        //Given
        Product aspirin = Product.builder()
                .name("aspirin")
                .price(new BigDecimal("1.2"))
                .priceCurrency("GBP")
                .build();
        Product lempsip = Product.builder()
                .name("lempsip")
                .price(new BigDecimal("1.2"))
                .priceCurrency("GBP")
                .build();
        productService.add(aspirin);
        productService.add(lempsip);
        List<Product> expectedList = Arrays.asList(aspirin, lempsip);
        when(productService.list()).thenReturn(expectedList);
        //when
        ResultActions resultActions = mockMvc.perform(get("/products"));
        //Then
        verify(productService, times(1)).list();
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedList)));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        //Given
        Product aspirin = Product.builder()
                .name("ASPIRIN")
                .price(new BigDecimal("2.7"))
                .priceCurrency("CHF")
                .productId(1L)
                .build();
        ProductUpdateDto toUpdateDto = ProductUpdateDto.builder()
                .name("ASPIRIN")
                .price(new BigDecimal("2.7"))
                .priceCurrency("CHF")
                .build();

        when(productService.update(anyLong(),any(ProductUpdateDto.class))).thenReturn(aspirin);
        //when
        ResultActions restCallResponse = mockMvc.perform(
                put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toUpdateDto)));
        //Then
        verify(productService, times(1)).update(anyLong(), Mockito.any(ProductUpdateDto.class));
        restCallResponse
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(aspirin)));
    }

    @Test
    public void shouldReturnNotFound_whenProductIsNotInTheSystem() throws Exception {
        //Given
        ProductUpdateDto toUpdateDto = ProductUpdateDto.builder()
                .name("ASPIRIN")
                .price(new BigDecimal("2.7"))
                .priceCurrency("CHF")
                .build();

        when(productService.update(anyLong(),any(ProductUpdateDto.class))).thenThrow(ProductNotFoundException.class);
        //when
        ResultActions restCallResponse = mockMvc.perform(
                put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toUpdateDto)));
        //Then
        verify(productService, times(1)).update(anyLong(), Mockito.any(ProductUpdateDto.class));
        restCallResponse
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnServiceUnavailable_whenUnexpectedExceptionThrown() throws Exception {
        //Given
        ProductUpdateDto toUpdateDto = ProductUpdateDto.builder()
                .name("ASPIRIN")
                .price(new BigDecimal("2.7"))
                .priceCurrency("CHF")
                .build();

        when(productService.update(anyLong(),any(ProductUpdateDto.class))).thenThrow(RuntimeException.class);
        //when
        ResultActions restCallResponse = mockMvc.perform(
                put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toUpdateDto)));
        //Then
        verify(productService, times(1)).update(anyLong(), Mockito.any(ProductUpdateDto.class));
        restCallResponse
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        //Given
        Product aspirin = Product.builder()
                .name("ASPIRIN")
                .price(new BigDecimal("2.7"))
                .priceCurrency("CHF")
                .productId(1L)
                .build();
        //when
        ResultActions restCallResponse = mockMvc.perform(
                delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON));
        //Then
        restCallResponse.andExpect(status().isOk());
        verify(productService, times(1)).delete(1L);
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}

