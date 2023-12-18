package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.PagingResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductPriceService productPriceService;

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDsc())
                .build();
        productRepository.save(product);
        return ProductResponse.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .description(product.getDescription())
                .build();
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        assert product != null;
        return ProductResponse.builder()
                .id(id)
                .nameProduct(product.getName())
                .description(product.getDescription())
                .build();
    }

    @Override
    public List<Product> getAll() {
//        return productRepository.findAll();
        return productRepository.findAll();
//        return products.stream().map(product -> ProductResponse.builder()
//                .id(product.getId())
//                .nameProduct(product.getName())
//                .description(product.getDescription())
////                .priceList(product.getProductPrices())
//                .build()).collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(productRequest.getProductId())
                .name(productRequest.getName())
                .description(productRequest.getDsc())
                .build();
        productRepository.save(product);
        return ProductResponse.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .description(product.getDescription())
                .build();
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {
        StoreResponse storeResponse = storeService.getById(productRequest.getStoreId().getId());
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDsc())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .store(Store.builder()
                        .id(storeResponse.getId())
                        .build())
                .build();
        productPriceService.create(productPrice);
//        return null;
        return ProductResponse.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(storeResponse.getId())
                        .storeName(storeResponse.getStoreName())
                        .noSiup(storeResponse.getNoSiup())
                        .build())
                .build();
    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        //Specificaion untuk menentukan kriteria pencarian, disini criteria pencarian ditandakan dengan root, root yang dimaksud adalah entity product
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            //Join digunakan untuk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPriceJoin = root.join("productPrices");
            //Predicate digunakan untuk menggunakan Like dimana nanti kita akan menggunakan kondisi pencarian paramater
            //Disini kita akan mencari mana product atau harga yang sama atau harga dibwahnya, makannya menggunkan lessThanOrEqualsTo
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPriceJoin.get("price"), maxPrice));
            }
            // kode return mengembalikan query dimana pada dasarnya kita membangun klausa where yang sudah ditentukan dari predicate atau criteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(specification, pageable);
        //
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            //
            Optional<ProductPrice> productPrice = product.getProductPrices()//
                    .stream().filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty()) continue;//
            Store store = productPrice.get().getStore();//
            productResponses.add(toProductResponse(store, product, productPrice.get()));
        }
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    private static ProductResponse toProductResponse(Store store, Product product, ProductPrice productPrice) {
        return ProductResponse.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .build())
                .build();
    }
}
