package com.enigma.shopeymart.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "m_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "admin_name", nullable = false, length = 150)
    private String name;

    @Column(name = "mobile_phone_no", unique = true, nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_credentials_id")
    private UserCredentials userCredentials;
}
