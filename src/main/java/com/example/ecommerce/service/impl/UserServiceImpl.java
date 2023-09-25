package com.example.ecommerce.service.impl;

import java.util.*;

import com.example.ecommerce.DTO.UserRegistrationDto;
import com.example.ecommerce.dao.RoleRepository;
import com.example.ecommerce.exeptions.RegistrationException;
import com.example.ecommerce.model.Role;
import org.apache.log4j.Logger;

import com.example.ecommerce.dao.UserRepository;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<User> findAll() {
        try{
            return userRepository.findAll();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        if(registrationDto.getPassword().equals(registrationDto.getConfirmPassword())){
            User existsUser = userRepository.findByEmail(registrationDto.getEmail());
            if(existsUser == null) {
                Role role = roleRepository.findById(1).orElse(null);
                User user = new User(
                        registrationDto.getName(), registrationDto.getEmail(),
                        registrationDto.getPassword(), role);
                return userRepository.save(user);
            }else{
                throw new RegistrationException("Email already exists");
            }
        }else{
            throw new RegistrationException("The Confirm Password confirmation does not match");
        }
    }


}