package com.pharma.productmanagement.service;

import com.pharma.productmanagement.domain.Product;
import com.pharma.productmanagement.dto.ProductUpdateDto;
import com.pharma.productmanagement.repository.ProductRepository;
import com.pharma.productmanagement.service.exceptions.ProductNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private Clock clock;
    @InjectMocks
    private ProductService productService;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    public void shouldCreateAProduct_givenAValidProduct() {
        //Given
        Clock fixedClock = Clock.fixed(Instant.parse("2020-04-12T01:00:00Z"), ZoneId.of("UTC"));
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        Product product = Product.builder()
                .name("aspirin")
                .price(new BigDecimal("1.2"))
                .priceCurrency("GBP")
                .build();
        //when
        productService.add(product);
        //Then
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void shouldListAllProducts() {
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
        List<Product> expectedList = Arrays.asList(aspirin, lempsip);
        when(productRepository.findAll()).thenReturn(expectedList);
        //when
        List<Product> actualList = productService.list();
        //Then
        verify(productRepository, times(1)).findAll();
        Assertions.assertThat(actualList).hasSameElementsAs(expectedList);
    }

    @Test
    public void shouldUpdateProduct() {
        //Given
        Product aspirin = Product.builder()
                .name("aspirin-lovercase")
                .price(new BigDecimal("1.2"))
                .priceCurrency("GBP")
                .productId(1L)
                .build();
        ProductUpdateDto toUpdateDto = ProductUpdateDto.builder()
                .name("ASPIRIN")
                .price(new BigDecimal("2.7"))
                .priceCurrency("CHF")
                .build();

        when(productRepository.findById(aspirin.getProductId())).thenReturn(Optional.of(aspirin));
        //when
        productService.update(aspirin.getProductId(),toUpdateDto);
        //Then
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product updatedProduct = productArgumentCaptor.getValue();
        Assertions.assertThat(updatedProduct.getName()).isEqualTo(toUpdateDto.getName());
        Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(toUpdateDto.getPrice());
        Assertions.assertThat(updatedProduct.getPriceCurrency()).isEqualTo(toUpdateDto.getPriceCurrency());
    }

    @Test
    public void shouldDeleteProduct() {
        //Given
        Product lempsip = Product.builder()
                .isActive(true)
                .productId(5L)
                .name("lempsip")
                .price(new BigDecimal("2.1"))
                .priceCurrency("GBP")
                .build();
        when(productRepository.findById(5L)).thenReturn(Optional.of(lempsip));
        //when
        productService.delete(5L);
        //Then
        lempsip.setActive(false);
        verify(productRepository, times(1)).save(lempsip);
    }

    @Test
    public void deleteShouldThrowException_whenProductIsNotFound() {
        //Given
        Product lempsip = Product.builder()
                .productId(7L)
                .name("lempsip")
                .price(new BigDecimal("1.2"))
                .priceCurrency("GBP")
                .build();
        when(productRepository.findById(7L)).thenReturn(Optional.empty());

        //when //Then
        assertThatThrownBy(() ->  productService.delete(7L)).isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, never()).save(lempsip);
    }
}
