package com.enigma.shopeymart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
* !!! IMPORTANT !!!
* attribute yang ada pada StoreRequest digunakan pada s
* Method Get Dan Update
* Jangan Keliru
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StoreRequest {

    private String id;
    private String noSiup;
    private String name;
    private String address;
    private String mobilePhone;


}
