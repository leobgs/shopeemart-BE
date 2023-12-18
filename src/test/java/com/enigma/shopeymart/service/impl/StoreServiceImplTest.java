package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class StoreServiceImplTest {

    // mock store repositrory
    private final StoreRepository storeRepository = mock(StoreRepository.class);

    private final StoreService storeService = new StoreServiceImpl(storeRepository);


    @Test
    void itShouldReturnStoreWhenCreateNewStore() {
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("123");
        dummyStoreRequest.setName("Jaya selalu");

        //perform then create operation
        StoreResponse storeResponse = storeService.create(dummyStoreRequest);

        verify(storeRepository).save(any(Store.class));
        assertEquals(dummyStoreRequest.getName() , storeResponse.getStoreName());
    }

    @Test
    void itShouldGetAllDataStoreWhenCallGetAll(){
        List<Store>dummyStore = new ArrayList<>();
        dummyStore.add(new Store("1","2132","berkah abadi","ragunan", "086237"));
        dummyStore.add(new Store("2","2122","berkah imortal","ragunin", "08623734"));

        when(storeRepository.findAll()).thenReturn(dummyStore);
        List<StoreResponse>retriveStore = storeService.getAll();

        assertEquals(dummyStore.size(),retriveStore.size());

        for (int i = 0; i < dummyStore.size(); i++) {
            assertEquals(dummyStore.get(i).getName(),retriveStore.get(i).getStoreName());
        }

    }

    @Test
    void itShouldGetDataStoreOneWhenGetByIdStore(){
        String storeId = "1";
        Store store =   new Store("1","2132","berkah abadi","ragunan", "086237");
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        StoreResponse storeResponse = storeService.getById(storeId);

        verify(storeRepository).findById(storeId);

        verify(storeRepository).findById(storeId);
        assertNotNull(storeResponse);
        assertEquals("berkah abadi", storeResponse.getStoreName());
    }

    @Test
    void itShouldDeleteDataStoreOneWhenDeleteById(){
        String id = "1";
        storeService.delete(id);
        verify(storeRepository, times(1)).deleteById(id);
    }

    @Test
    void itSholdUpdateDataStoreWhenUpdateStore(){
        Store storeDummy = new Store("1", "4234", "jaya abadi", "jonggol", "08963432");

        StoreRequest storeRequest = new StoreRequest();
        storeRequest.setId("1");
        storeRequest.setNoSiup("3213");
        storeRequest.setName("rumah kita");
        storeRequest.setAddress("Jkarta");
        storeRequest.setMobilePhone("930846");

        when(storeRepository.findById(storeRequest.getId())).thenReturn(Optional.of(storeDummy));

        StoreResponse storeResponse = storeService.update(storeRequest);

        verify(storeRepository,times(1)).findById(storeRequest.getId());

        verify(storeRepository,times(1)).save(storeDummy);

        assertEquals(storeRequest.getId(),storeResponse.getId());
        assertEquals(storeRequest.getNoSiup(),storeResponse.getNoSiup());
        assertEquals(storeRequest.getName(),storeResponse.getStoreName());
        assertEquals(storeRequest.getAddress(),storeResponse.getAddress());
        assertEquals(storeRequest.getMobilePhone(),storeResponse.getPhone());
    }

}