package com.enigma.shopeymart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // untuk mendapatkan getter setter
@AllArgsConstructor // untuk mendapatkan semua constructor
@NoArgsConstructor // untuk mendapatkan constructor kosong
@Builder(toBuilder = true) // Untuk memudahkan mapping
public class StoreResponse {

    private String id;
    private String noSiup;
    private String storeName;
    private String address;
    private String phone;


}
