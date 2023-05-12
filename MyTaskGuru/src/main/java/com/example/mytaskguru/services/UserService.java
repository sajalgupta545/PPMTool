package com.example.mytaskguru.services;
import com.example.mytaskguru.domain.User;
import com.example.mytaskguru.exceptions.UsernameAlreadyExistsException;
import com.example.mytaskguru.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class UserService {

    private static final Logger logger= LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){

        try{
            logger.info("[saveUser] - "+newUser.toString());
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            User u=userRepository.save(newUser);
            logger.info("[RESULT - saveUser] - "+u.toString());
            return u;

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }

    }



}
