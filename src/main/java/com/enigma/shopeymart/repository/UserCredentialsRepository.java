package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String> {
Optional<UserCredentials>findByUsername(String username);
}

