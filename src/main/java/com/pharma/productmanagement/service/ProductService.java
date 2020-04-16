package com.pharma.productmanagement.service;


import com.pharma.productmanagement.domain.Product;
import com.pharma.productmanagement.dto.ProductUpdateDto;
import com.pharma.productmanagement.repository.ProductRepository;
import com.pharma.productmanagement.service.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    private Clock clock;

    public Product add(Product product) {
        product.setCreationDate(LocalDate.now(clock));
        product.setActive(true);
        return productRepository.save(product);
    }

    public List<Product> list() {
        return (List<Product>) productRepository.findAll();
    }

    public Product update(long productId, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productId + " not found"));

        product.setName(productUpdateDto.getName());
        product.setPrice(productUpdateDto.getPrice());
        product.setPriceCurrency(productUpdateDto.getPriceCurrency());

        return productRepository.save(product);
    }


    public void delete(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));

        product.setActive(false);

        productRepository.save(product);
    }

}
