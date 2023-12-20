package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.PagingResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
        public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProductAndProductPrice(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Succesfully created new product")
                        .data(productResponse)
                        .build());
    }
    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public List<Product>getAllProduct(){
        return productService.getAll();
    }

    @GetMapping("/page")
    public ResponseEntity<?>getAllProductsPage(
            @RequestParam(name = "name",required = false)String name,
            @RequestParam(name = "maxPrice", required = false)Long maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "0")Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5")Integer size
    ){
        Page<ProductResponse>productResponses = productService.getAllByNameOrPrice(name,maxPrice,page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(CommonResponse.builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Succesfully get all products")
                    .data(productResponses)
                    .paging(pagingResponse)
                    .build());
    }
}
