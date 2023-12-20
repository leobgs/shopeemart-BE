package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void createProduct() {
        //todo -> Data dummy
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Biskuat");
        productRequest.setPrice(100L);
        productRequest.setDsc("Semua bisa jadi macan!");
        productRequest.setStock(20);

        //todo -> Data response
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId("1");
        productResponse.setNameProduct("Biskuat");
        productResponse.setPrice(100L);

        //todo -> Check behavior on service then return responseProduct
        when(productService.createProductAndProductPrice(productRequest)).thenReturn(productResponse);

        //todo -> Check behavior on controller using productRequest
        ResponseEntity<?> responseEntity = productController.createProduct(productRequest);

        //todo ->
        assertEquals(CREATED,responseEntity.getStatusCode());

        CommonResponse<ProductResponse> commonResponse =
                (CommonResponse<ProductResponse>) responseEntity.getBody();

        assertEquals(CREATED.value(), commonResponse.getStatusCode());
        assertEquals("Succesfully created new product", commonResponse.getMessage());
    }
}