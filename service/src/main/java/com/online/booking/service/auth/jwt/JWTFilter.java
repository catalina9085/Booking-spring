package com.online.booking.service.auth.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.online.booking.service.auth.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 In JWTFilter, autentificarea nu mai trebuie verificata din nou cu AuthenticationManager pentru ca,
Clientul deja a trecut prin login() si are un token JWT valid.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {
    private CustomUserDetailsService userDetailsService;
    private JWTUtil jwtUtil;

    public JWTFilter(CustomUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.jwtUtil=jwtUtil;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI();
        if (path.equals("/auth/login") || path.equals("/auth/register")) {
            chain.doFilter(request, response);
            return;
        }
        String header=request.getHeader("Authorization");
        if(header!=null && !header.isBlank() && header.startsWith("Bearer ")) {
            String jwt=header.substring(7);
            if(jwt==null || jwt.isBlank())
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid token!");
            else {
                try {
                    String email=jwtUtil.validateToken(jwt);
                    System.out.println("\n\n\n!!!!!!!!!!!!"+jwt);
                    UserDetails userDetails=userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authToken=
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                    if(SecurityContextHolder.getContext().getAuthentication()==null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch(JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid token!");
                }
            }
        }
        chain.doFilter(request, response);
    }
}