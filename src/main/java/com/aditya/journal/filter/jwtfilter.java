package com.aditya.journal.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.service.UserDetailService;
import com.aditya.journal.service.UserEntryService;
import com.aditya.journal.util.JWTutil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class jwtfilter extends OncePerRequestFilter{

    @Autowired 
    private JWTutil jwtutil;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
       String header = request.getHeader("Authorization");
       String username = null;
       String jwt = null;
       if(header!=null && header.startsWith("Bearer ")){
        jwt = header.substring(7);
        username = jwtutil.extractUsername(jwt);
       }

       if(username !=null && jwtutil.validateToken(jwt)){
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
       }
       filterChain.doFilter(request, response);
    }
}
