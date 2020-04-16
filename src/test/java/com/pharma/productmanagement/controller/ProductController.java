package com.pharma.productmanagement.controller;


import com.pharma.productmanagement.domain.Product;
import com.pharma.productmanagement.dto.ProductUpdateDto;
import com.pharma.productmanagement.service.ProductService;
import com.pharma.productmanagement.service.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {

    private ProductService productService;

    @PostMapping(value="/products",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Product postProduct(@RequestBody Product product) {
        return productService.add(product);
    }

    @GetMapping(value="/products",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> postProduct() {
        return productService.list();
    }

    @PutMapping(value="/products/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long productId,@RequestBody ProductUpdateDto productDto) {
        return productService.update(productId,productDto);
    }

    @DeleteMapping(value="/products/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleException(ProductNotFoundException ex) {
        log.info(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleException(Exception ex) {
        log.error("Unexpected error", ex);
    }

}
