package com.example.ecommerce.service.impl;

import java.util.Collections;
import java.util.List;
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
}