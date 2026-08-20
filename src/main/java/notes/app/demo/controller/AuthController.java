package notes.app.demo.controller;
//
//package com.notesapp.notes.controller;

import notes.app.demo.entity.User;
import notes.app.demo.repository.UserRepository;
import notes.app.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody Map<String, String> request
    ) {

        try {

            String username =
                    request.get("username");

            String password =
                    request.get("password");

            User user =
                    userService.register(
                            username,
                            password
                    );

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Registration successful",
                            "username",
                            user.getUsername()
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpRequest
    ) {

        String username =
                request.get("username");

        String password =
                request.get("password");

        if (username == null ||
                password == null) {

            return ResponseEntity.badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    "Username and password are required"
                            )
                    );
        }

        User user =
                userRepository
                        .findByUsername(username.trim())
                        .orElse(null);

        if (user == null ||
                !passwordEncoder.matches(
                        password,
                        user.getPassword()
                )) {

            return ResponseEntity.status(401)
                    .body(
                            Map.of(
                                    "error",
                                    "Invalid username or password"
                            )
                    );
        }

        HttpSession session =
                httpRequest.getSession(true);

        session.setAttribute(
                "USERNAME",
                user.getUsername()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Login successful",
                        "username",
                        user.getUsername()
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request
    ) {

        HttpSession session =
                request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Logout successful"
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(
            HttpServletRequest request
    ) {

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            return ResponseEntity.status(401)
                    .body(
                            Map.of(
                                    "error",
                                    "Not logged in"
                            )
                    );
        }

        String username =
                (String) session.getAttribute(
                        "USERNAME"
                );

        if (username == null) {

            return ResponseEntity.status(401)
                    .body(
                            Map.of(
                                    "error",
                                    "Not logged in"
                            )
                    );
        }

        return ResponseEntity.ok(
                Map.of(
                        "username",
                        username
                )
        );
    }
}
