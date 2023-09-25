package com.example.ecommerce.service;

import com.example.ecommerce.DTO.UserRegistrationDto;
import com.example.ecommerce.model.User;

public interface UserService extends BaseService<Long, User> {

    User save(UserRegistrationDto registrationDto );
}