package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository  extends JpaRepository<Order, String> {
}
