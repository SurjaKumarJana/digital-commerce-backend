package org.surja.digital_commerce_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.surja.digital_commerce_backend.entity.User;
import org.surja.digital_commerce_backend.repo.UserRepo;

@Service
public class AppUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user == null){
            throw  new UsernameNotFoundException("User Does not exist");
        }
        return user;
    }
}
