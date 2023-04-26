package com.speproject.ppmtool.services;

import com.speproject.ppmtool.domain.User;
import com.speproject.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.speproject.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Password Encrypter coming from spring security
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("");
            //Edge cases
            // Username should be unique(exception)
            // make sure password and confirm password matches
            // we dont persist or show the confirm password

            return userRepository.save(newUser);
        }catch(Exception e){
                throw new UsernameAlreadyExistsException("username '"+newUser.getUsername()+"' already exists");
        }

    }


}
