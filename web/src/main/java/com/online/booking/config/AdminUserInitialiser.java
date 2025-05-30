package com.online.booking.config;

import com.online.booking.entities.User;
import com.online.booking.repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitialiser implements CommandLineRunner {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public AdminUserInitialiser(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!userRepo.existsByEmail("admin@gmail.com")) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setRole("ROLE_ADMIN");
            userRepo.save(adminUser);
        }
    }

}
