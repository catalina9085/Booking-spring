package com.online.booking.service.auth;


import com.online.booking.entities.User;
import com.online.booking.repos.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user=userRepo.findByEmail(email);
        User foundUser=user.orElseThrow(()->new UsernameNotFoundException("The user "+email+ " does not exist!"));

        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(),
                List.of(new SimpleGrantedAuthority(foundUser.getRole())));
        return user1;
    }
}