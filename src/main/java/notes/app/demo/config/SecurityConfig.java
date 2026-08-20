//package notes.app.demo.config;
//
////package com.notesapp.notes.config;
//
//import notes.app.demo.security.SessionAuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    private final SessionAuthenticationFilter
//            sessionAuthenticationFilter;
//
//    public SecurityConfig(
//            SessionAuthenticationFilter sessionAuthenticationFilter
//    ) {
//        this.sessionAuthenticationFilter =
//                sessionAuthenticationFilter;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http
//    ) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers(
//                                "/",
//                                "/index.html",
//                                "/login.html",
//                                "/register.html",
//                                "/css/**",
//                                "/js/**",
//                                "/api/auth/register",
//                                "/api/auth/login",
//                                "/api/auth/me"
//                        ).permitAll()
//
//                        .anyRequest()
//                        .authenticated()
//                )
//
//                .addFilterBefore(
//                        sessionAuthenticationFilter,
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }
//}


package notes.app.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SessionAuthenticationFilter sessionAuthenticationFilter;

    public SecurityConfig(
            SessionAuthenticationFilter sessionAuthenticationFilter
    ) {
        this.sessionAuthenticationFilter =
                sessionAuthenticationFilter;
    }


    // =====================================================
    // PASSWORD ENCODER
    // =====================================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // =====================================================
    // SECURITY FILTER CHAIN
    // =====================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // =========================================
                // CSRF
                // =========================================

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/api/notes/**",
                                "/api/auth/**"
                        )
                )


                // =========================================
                // SESSION
                // =========================================

                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )


                // =========================================
                // AUTHORIZATION
                // =========================================

                .authorizeHttpRequests(auth -> auth

                        // ---------------------------------
                        // PUBLIC PAGES
                        // ---------------------------------

                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/login.html",
                                "/register.html",

                                "/css/**",
                                "/js/**",
                                "/images/**",

                                "/favicon.ico"
                        )
                        .permitAll()


                        // ---------------------------------
                        // AUTH API
                        // ---------------------------------

                        .requestMatchers(
                                "/api/auth/**"
                        )
                        .permitAll()


                        // ---------------------------------
                        // NOTES API
                        // ---------------------------------

                        .requestMatchers(
                                "/api/notes/**"
                        )
                        .authenticated()


                        // ---------------------------------
                        // EVERYTHING ELSE
                        // ---------------------------------

                        .anyRequest()
                        .authenticated()
                )


                // =========================================
                // SESSION AUTHENTICATION FILTER
                // =========================================

                .addFilterBefore(
                        sessionAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}