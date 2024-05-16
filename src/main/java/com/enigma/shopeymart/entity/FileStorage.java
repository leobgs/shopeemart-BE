package com.enigma.shopeymart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class FileStorage {
    private String fileName;
    private LocalDateTime dateTime;
}
