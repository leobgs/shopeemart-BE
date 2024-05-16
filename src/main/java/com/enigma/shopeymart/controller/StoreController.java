package com.enigma.shopeymart.controller;


import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE)
public class StoreController {
    private final StoreService storeService;
//
//    @PostMapping("/store")
//    public Store createStore(@RequestBody Store store) {
//        return storeService.create(store);
//    }

//    @GetMapping("/get/{id}")
//    public Store getByIdOrder(@PathVariable String id) {
//        return storeService.getById(id);
//    }

//    @GetMapping("/store")
//    public List<Store> getAllStore() {
//        return storeService.getAll();
//    }
//
//    @PutMapping("/store/{id}")
//    public Store updateStore(@PathVariable String id, @RequestBody Store store) {
//        store.setId(id);
//        return storeService.update(store);
//    }
//
//    @DeleteMapping("store/{id}")
//    public void deleteStore(@PathVariable String id) {
//        storeService.delete(id);
//    }

    @PostMapping("/")
    public StoreResponse createStore(@RequestBody StoreRequest storeRequest) {
        return storeService.create(storeRequest);
    }

    @GetMapping("/{id}")
    public StoreResponse getByIdOrder(@PathVariable String id) {
        return storeService.getById(id);
    }

    @GetMapping("")
    public List<StoreResponse> getAllStore() {
        return storeService.getAll();
    }

    @PutMapping("/")
    public StoreResponse updateStore(@RequestBody StoreRequest storeRequest) {
//        storeRequest.setId(id);
        return storeService.update(storeRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable String id) {
        storeService.delete(id);
    }
}
