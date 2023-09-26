package com.example.ecommerce.dao;

import com.example.ecommerce.model.RoleName;
import com.example.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Page<User> findByRoleName(RoleName roleName, Pageable pageable);

    Page<User> findByFullnameContainingAndRoleName(String name, RoleName roleName, Pageable pageable);

}
