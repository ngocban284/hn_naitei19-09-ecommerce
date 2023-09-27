package com.example.ecommerce.service;

import com.example.ecommerce.DTO.UserRegistrationDto;
import com.example.ecommerce.model.User;
import org.springframework.data.domain.Page;


public interface UserService extends BaseService<Long, User> {
    Page<User> findAllPaginated(int page, int size, String sortField, String sortOrder);

    User save(UserRegistrationDto registrationDto);

    Page<User> searchByFullname(String name, int page, int size, String sortField, String sortOrder);

}