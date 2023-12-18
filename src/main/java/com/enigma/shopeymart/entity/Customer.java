package com.enigma.shopeymart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Mengatotasikan Entity customer sebagai table
@Table(name = "m_customer") // menganotasika entity customer ke database pada table m_customer
@Data // Membuat getter setter menggunkan lombok
@AllArgsConstructor // membuat constructor yang lengkap
@NoArgsConstructor // membuat constructor kosong
@Builder(toBuilder = true) // memapingkan saat menggunakan DTO
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String name;

    @Column(name = "mobile_phone_no", unique = true, nullable = false, length = 20)
    private String phone;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @OneToOne
    @JoinColumn
    private UserCredentials userCredentials;

}
