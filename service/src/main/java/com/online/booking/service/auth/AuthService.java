package com.online.booking.service.auth;


import com.online.booking.entities.User;
import com.online.booking.repos.UserRepo;
import com.online.booking.service.auth.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {
    private UserRepo userRepo;
    private PasswordEncoder encoder;
    private AuthenticationManager authManager;
    private JWTUtil util;

    public AuthService(UserRepo userRepo, PasswordEncoder encoder, AuthenticationManager authManager, JWTUtil util) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.util = util;
    }

    public void register(Map<String,String> req){
        if(userRepo.existsByEmail(req.get("email"))){
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setEmail(req.get("email"));
        user.setPassword(encoder.encode(req.get("password")));
        user.setRole("ROLE_USER");
        userRepo.save(user);
    }

    public Map<String,Object> login(Map<String,String> req) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(req.get("email"), req.get("password"));
            authManager.authenticate(authToken);//daca nu corespund datele,va da eroare
            //si se impiedica astfel trimiterea tokenului
            User user=userRepo.findByEmail(req.get("email")).orElseThrow(()->new RuntimeException("User Not Found"));
            String token = util.generateToken(user.getEmail(),user.getRole());
            return Collections.singletonMap("jwtToken", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}
