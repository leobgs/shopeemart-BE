package com.enigma.shopeymart.service;

import com.enigma.shopeymart.entity.ProductPrice;
import org.springframework.stereotype.Repository;

public interface ProductPriceService {
    ProductPrice create(ProductPrice productPrice);

    ProductPrice getById(String id);
    ProductPrice findProductPriceIsActive(String productId,Boolean active);
}
