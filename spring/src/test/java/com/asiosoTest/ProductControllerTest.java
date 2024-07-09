package com.asiosoTest; // Ensure this matches your directory structure

import com.asioso.ProductController;
import com.asioso.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = Mockito.mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    void testGetAllProducts() {
        String mockResponse = "[{\"id\":1,\"name\":\"Banana\"},{\"id\":2,\"name\":\"Blackberry\"}]";
        when(productService.findAllProducts()).thenReturn(Mono.just(mockResponse));
        assertEquals(HttpStatus.OK, productController.getAllProducts().block().getStatusCode());
        assertEquals(mockResponse, productController.getAllProducts().block().getBody());
    }

    @Test
    void testGetOneProductData() {
        String mockResponse = "{\"id\":1,\"name\":\"Banana\"}";
        when(productService.findProduct(anyInt())).thenReturn(Mono.just(mockResponse));
        assertEquals(HttpStatus.OK, productController.getOneProductData(1).block().getStatusCode());
        assertEquals(mockResponse, productController.getOneProductData(1).block().getBody());
    }
}
