package com.pharma.productmanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharma.productmanagement.ProductManagementApplication;
import com.pharma.productmanagement.domain.Product;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProductManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class SpringCucumberBaseClass {

    @Autowired
    private ObjectMapper objectMapper;

    private  static RestTemplate restTemplate = new RestTemplate();

    public void postProduct(Product product)  {
        HttpEntity<Product> request = new HttpEntity(product);

        restTemplate.postForObject("http://localhost:8080/products", request, Product.class);
    }


    public List<Product> listProducts() throws Exception {
        return Arrays.asList(restTemplate.getForObject(
                "http://localhost:8080/products",
                Product[].class));
    }




    private String asJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
