package com.speproject.ppmtool.services;

import com.speproject.ppmtool.domain.User;
import com.speproject.ppmtool.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);

        if(user == null) new UsernameNotFoundException("User not found");
        return user;
    }
    @Transactional
    public User loadUserById(Long id){
        User user = userRepository.getById(id);
        Hibernate.unproxy(user, User.class);
        if(user == null) new UsernameNotFoundException("User not found");
        return user;

    }
}

// checkong workonh
