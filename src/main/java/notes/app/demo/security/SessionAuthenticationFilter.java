//package notes.app.demo.security;
//
////package com.notesapp.notes.security;
//
//import notes.app.demo.entity.User;
//import notes.app.demo.repository.UserRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public class SessionAuthenticationFilter
//        extends OncePerRequestFilter {
//
//    private final UserRepository userRepository;
//
//    public SessionAuthenticationFilter(
//            UserRepository userRepository
//    ) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        String username =
//                (String) request.getSession()
//                        .getAttribute("USERNAME");
//
//        if (username != null &&
//                SecurityContextHolder
//                        .getContext()
//                        .getAuthentication() == null) {
//
//            userRepository
//                    .findByUsername(username)
//                    .ifPresent(user -> {
//
//                        UsernamePasswordAuthenticationToken authentication =
//                                new UsernamePasswordAuthenticationToken(
//                                        user.getUsername(),
//                                        null,
//                                        List.of(
//                                                new SimpleGrantedAuthority(
//                                                        "ROLE_USER"
//                                                )
//                                        )
//                                );
//
//                        SecurityContextHolder
//                                .getContext()
//                                .setAuthentication(
//                                        authentication
//                                );
//                    });
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

package notes.app.demo.security;

import notes.app.demo.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class SessionAuthenticationFilter
        extends OncePerRequestFilter {


    private final UserRepository userRepository;


    public SessionAuthenticationFilter(
            UserRepository userRepository
    ) {

        this.userRepository =
                userRepository;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        // ================================================
        // GET USERNAME FROM SESSION
        // ================================================

        String username =
                (String)
                        request
                                .getSession()
                                .getAttribute(
                                        "USERNAME"
                                );


        // ================================================
        // CREATE SPRING SECURITY AUTHENTICATION
        // ================================================

        if (
                username != null
                        &&
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                == null
        ) {


            userRepository
                    .findByUsername(username)
                    .ifPresent(user -> {


                        UsernamePasswordAuthenticationToken
                                authentication =

                                new UsernamePasswordAuthenticationToken(

                                        user.getUsername(),

                                        null,

                                        List.of(
                                                new SimpleGrantedAuthority(
                                                        "ROLE_USER"
                                                )
                                        )
                                );


                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(
                                        authentication
                                );

                    });
        }


        // ================================================
        // CONTINUE REQUEST
        // ================================================

        filterChain.doFilter(
                request,
                response
        );
    }
}