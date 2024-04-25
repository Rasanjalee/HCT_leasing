package com.amarasiricoreservice.service;

import com.amarasiricoreservice.Repository.UserRepository;
import com.amarasiricoreservice.entity.UserMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveUser(UserMaster userMaster){
        userRepository.save(userMaster);
    }
}
