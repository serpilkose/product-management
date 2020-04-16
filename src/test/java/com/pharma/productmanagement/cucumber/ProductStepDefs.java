package com.pharma.productmanagement.cucumber;

import com.pharma.productmanagement.controller.SpringCucumberBaseClass;
import com.pharma.productmanagement.domain.Product;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ProductStepDefs extends SpringCucumberBaseClass {

    List<Product> givenProducts;

    @Given("^I have the following products$")
    public void i_have_the_following_products(List<Product> products) throws Throwable {
        products.forEach(System.out::println);
        givenProducts = products;
    }

    @When("^I add the products to the system$")
    public void i_add_the_products_to_the_system() throws Throwable {
       givenProducts.forEach(product -> {
           postProduct(product);
       });
    }

    @Then("^I will see those products in the system$")
    public void i_will_see_those_products_in_the_system(List<Product> expectedProducts) throws Throwable {
        List<Product> actualProducts = listProducts();
        Assertions.assertThat(expectedProducts.size()).isEqualTo(actualProducts.size());
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        List<Product> expectedArrayList = new ArrayList<>();
        expectedProducts.forEach(o -> expectedArrayList.add(o));
        actualProducts.sort(productComparator);
        expectedArrayList.sort(productComparator);
        IntStream.range(0,expectedArrayList.size()).forEach(ind ->
                Assertions.assertThat(isEqualTo(actualProducts.get(ind),expectedArrayList.get(ind))));
    }

    private boolean isEqualTo(Product expectedProduct,Product actualProduct) {
        // | productId | name    | price | priceCurrency |
        return expectedProduct.getProductId().equals(actualProduct.getProductId()) &&
                expectedProduct.getName().equals(actualProduct.getName()) &&
                expectedProduct.getPriceCurrency().equals(actualProduct.getPriceCurrency()) &&
                expectedProduct.getPrice().equals(actualProduct.getPrice());
    }

}
