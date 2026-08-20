//package notes.app.demo.controller;
//
////package com.notesapp.notes.controller;
//
//import notes.app.demo.entity.Note;
//import notes.app.demo.entity.User;
//import notes.app.demo.service.NoteService;
//import notes.app.demo.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/notes")
//@RequiredArgsConstructor
//public class NoteController {
//
//    private final NoteService noteService;
//    private final UserService userService;
//
//    private User getLoggedInUser(
//            HttpSession session
//    ) {
//
//        String username =
//                (String) session.getAttribute(
//                        "USERNAME"
//                );
//
//        if (username == null) {
//            throw new IllegalStateException(
//                    "User is not logged in"
//            );
//        }
//
//        return userService.findByUsername(username);
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getAllNotes(
//            HttpSession session
//    ) {
//
//        try {
//
//            User user =
//                    getLoggedInUser(session);
//
//            List<Note> notes =
//                    noteService.getAllNotes(user);
//
//            return ResponseEntity.ok(notes);
//
//        } catch (Exception e) {
//
//            return ResponseEntity.status(401)
//                    .body(
//                            Map.of(
//                                    "error",
//                                    "Unauthorized"
//                            )
//                    );
//        }
//    }
//
//    @GetMapping("/today")
//    public ResponseEntity<?> getTodayNotes(
//            HttpSession session
//    ) {
//
//        try {
//
//            User user =
//                    getLoggedInUser(session);
//
//            return ResponseEntity.ok(
//                    noteService.getTodayNotes(user)
//            );
//
//        } catch (Exception e) {
//
//            return ResponseEntity.status(401)
//                    .body(
//                            Map.of(
//                                    "error",
//                                    "Unauthorized"
//                            )
//                    );
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getNote(
//            @PathVariable Long id,
//            HttpSession session
//    ) {
//
//        try {
//
//            User user =
//                    getLoggedInUser(session);
//
//            return ResponseEntity.ok(
//                    noteService.getNote(id, user)
//            );
//
//        } catch (Exception e) {
//
//            return ResponseEntity.status(404)
//                    .body(
//                            Map.of(
//                                    "error",
//                                    "Note not found"
//                            )
//                    );
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createNote(
//            @RequestBody Map<String, String> request,
//            HttpSession session
//    ) {
//
//        try {
//
//            User user =
//                    getLoggedInUser(session);
//
//            Note note =
//                    noteService.createNote(
//                            user,
//                            request.get("title"),
//                            request.get("content")
//                    );
//
//            return ResponseEntity.ok(note);
//
//        } catch (Exception e) {
//
//            return ResponseEntity.badRequest()
//                    .body(
//                            Map.of(
//                                    "error",
//                                    e.getMessage()
//                            )
//                    );
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateNote(
//            @PathVariable Long id,
//            @RequestBody Map<String, String> request,
//            HttpSession session
//    ) {
//
//        try {
//
//            User user =
//                    getLoggedInUser(session);
//
//            Note note =
//                    noteService.updateNote(
//                            id,
//                            user,
//                            request.get("title"),
//                            request.get("content")
//                    );
//
//            return ResponseEntity.ok(note);
//
//        } catch (Exception e) {
//
//            return ResponseEntity.badRequest()
//                    .body(
//                            Map.of(
//                                    "error",
//                                    e.getMessage()
//                            )
//                    );
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteNote(
//            @PathVariable Long id,
//            HttpSession session
//    ) {
//
//        try {
//
//            User user =
//                    getLoggedInUser(session);
//
//            noteService.deleteNote(id, user);
//
//            return ResponseEntity.ok(
//                    Map.of(
//                            "message",
//                            "Note deleted"
//                    )
//            );
//
//        } catch (Exception e) {
//
//            return ResponseEntity.badRequest()
//                    .body(
//                            Map.of(
//                                    "error",
//                                    e.getMessage()
//                            )
//                    );
//        }
//    }
//}

package notes.app.demo.controller;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import notes.app.demo.entity.Note;
import notes.app.demo.entity.User;

import notes.app.demo.service.NoteService;
import notes.app.demo.service.UserService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {


    private final NoteService noteService;

    private final UserService userService;


    // =====================================================
    // GET LOGGED-IN USER
    // =====================================================

    private User getLoggedInUser(
            HttpSession session
    ) {

        String username =
                (String)
                        session.getAttribute(
                                "USERNAME"
                        );


        if (username == null) {

            throw new IllegalStateException(
                    "User is not logged in"
            );
        }


        User user =
                userService.findByUsername(
                        username
                );


        if (user == null) {

            throw new IllegalStateException(
                    "User not found"
            );
        }


        return user;
    }


    // =====================================================
    // GET ALL NOTES
    // =====================================================

    @GetMapping
    public ResponseEntity<?> getAllNotes(
            HttpSession session
    ) {

        try {

            User user =
                    getLoggedInUser(
                            session
                    );


            List<Note> notes =
                    noteService.getAllNotes(
                            user
                    );


            return ResponseEntity.ok(
                    notes
            );

        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .status(401)
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // GET TODAY'S NOTES
    // =====================================================

    @GetMapping("/today")
    public ResponseEntity<?> getTodayNotes(
            HttpSession session
    ) {

        try {

            User user =
                    getLoggedInUser(
                            session
                    );


            return ResponseEntity.ok(
                    noteService.getTodayNotes(
                            user
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .status(401)
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // GET ONE NOTE
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(
            @PathVariable Long id,
            HttpSession session
    ) {

        try {

            User user =
                    getLoggedInUser(
                            session
                    );


            return ResponseEntity.ok(
                    noteService.getNote(
                            id,
                            user
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of(
                                    "error",
                                    "Note not found"
                            )
                    );
        }
    }


    // =====================================================
    // CREATE NOTE
    // =====================================================

    @PostMapping
    public ResponseEntity<?> createNote(
            @RequestBody(
                    required = false
            )
            Map<String, String> request,

            HttpSession session
    ) {

        try {

            User user =
                    getLoggedInUser(
                            session
                    );


            String title = null;

            String content = null;


            if (request != null) {

                title =
                        request.get(
                                "title"
                        );

                content =
                        request.get(
                                "content"
                        );
            }


            Note note =
                    noteService.createNote(
                            user,
                            title,
                            content
                    );


            return ResponseEntity.ok(
                    note
            );

        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // UPDATE / AUTO SAVE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(
            @PathVariable Long id,

            @RequestBody
            Map<String, String> request,

            HttpSession session
    ) {

        try {

            User user =
                    getLoggedInUser(
                            session
                    );


            String title =
                    request.get(
                            "title"
                    );


            String content =
                    request.get(
                            "content"
                    );


            Note note =
                    noteService.updateNote(
                            id,
                            user,
                            title,
                            content
                    );


            return ResponseEntity.ok(
                    note
            );

        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // DELETE NOTE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(
            @PathVariable Long id,
            HttpSession session
    ) {

        try {

            User user =
                    getLoggedInUser(
                            session
                    );


            noteService.deleteNote(
                    id,
                    user
            );


            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Note deleted"
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();


            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "error",
                                    e.getMessage()
                            )
                    );
        }
    }
}