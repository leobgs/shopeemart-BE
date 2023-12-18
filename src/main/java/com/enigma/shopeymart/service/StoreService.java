package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {
//    Store create(Store store);

//    Store getById(String id);

//    List<Store> getAll();

//    Store update(Store store);
//
//    void delete(String id);

    StoreResponse create(StoreRequest storeRequest);//implement DTO sehingga tidak mengakses langsung dari entity

    StoreResponse getById(String id);

    List<StoreResponse> getAll();

    StoreResponse update(StoreRequest storeRequest);

    void delete(String id);

}
