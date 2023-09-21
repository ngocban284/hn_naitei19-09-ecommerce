package com.example.ecommerce.dao;

import com.example.ecommerce.model.OrderStatus;
import com.example.ecommerce.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
