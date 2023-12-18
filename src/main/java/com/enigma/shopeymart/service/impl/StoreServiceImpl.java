package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);
        return StoreResponse.builder()
                .id(store.getId())
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .phone(store.getMobilePhone())
                .address(store.getAddress())
                .build();
    }

    @Override
    public StoreResponse getById(String id) {
        Store store = storeRepository.findById(id).orElse(null);

        assert store != null;
        return StoreResponse.builder()
                .id(store.getId())
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .address(store.getAddress())
                .phone(store.getMobilePhone())
                .build();
    }

    @Override
    public List<StoreResponse> getAll() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(store -> StoreResponse.builder()
                .id(store.getId())
                .noSiup(store.getNoSiup())
                .storeName(store.getName())
                .address(store.getAddress())
                .phone(store.getMobilePhone())
                .build()).collect(Collectors.toList());
    }

    @Override
    public StoreResponse update(StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeRequest.getId()).orElse(null);
        assert store != null;
        store.setId(storeRequest.getId());
        store.setName(storeRequest.getName());
        store.setAddress(storeRequest.getAddress());
        store.setNoSiup(storeRequest.getNoSiup());
        store.setMobilePhone(storeRequest.getMobilePhone());
        storeRepository.save(store);
        return StoreResponse.builder()
                .id(store.getId())
                .storeName(store.getName())
                .address(store.getAddress())
                .noSiup(store.getNoSiup())
                .phone(store.getMobilePhone())
                .build();
    }

    @Override
    public void delete(String id) {
        storeRepository.deleteById(id);
    }
}
