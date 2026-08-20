//package notes.app.demo.service;
//
////package com.notesapp.notes.service;
//import notes.app.demo.entity.Note;
//import notes.app.demo.entity.User;
//import notes.app.demo.repository.NoteRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class NoteService {
//
//    private final NoteRepository noteRepository;
//
//    public List<Note> getAllNotes(User user) {
//
//        return noteRepository.findByUserOrderByUpdatedAtDesc(user);
//    }
//
//    public List<Note> getTodayNotes(User user) {
//
//        return noteRepository
//                .findByUserAndNoteDateOrderByUpdatedAtDesc(
//                        user,
//                        LocalDate.now()
//                );
//    }
//
//    public Note getNote(Long id, User user) {
//
//        return noteRepository
//                .findByIdAndUser(id, user)
//                .orElseThrow(() ->
//                        new IllegalArgumentException(
//                                "Note not found"
//                        )
//                );
//    }
//
//    public Note createNote(
//            User user,
//            String title,
//            String content
//    ) {
//
//        if (title == null || title.trim().isEmpty()) {
//            throw new IllegalArgumentException(
//                    "Title is required"
//            );
//        }
//
//        if (content == null) {
//            content = "";
//        }
//
//        Note note = new Note();
//
//        note.setTitle(title.trim());
//        note.setContent(content);
//        note.setUser(user);
//
//        return noteRepository.save(note);
//    }
//
//    public Note updateNote(
//            Long id,
//            User user,
//            String title,
//            String content
//    ) {
//
//        Note note = getNote(id, user);
//
//        if (title == null || title.trim().isEmpty()) {
//            throw new IllegalArgumentException(
//                    "Title is required"
//            );
//        }
//
//        note.setTitle(title.trim());
//        note.setContent(content == null ? "" : content);
//
//        return noteRepository.save(note);
//    }
//
//    public void deleteNote(Long id, User user) {
//
//        Note note = getNote(id, user);
//
//        noteRepository.delete(note);
//    }
//}

package notes.app.demo.service;

import lombok.RequiredArgsConstructor;

import notes.app.demo.entity.Note;
import notes.app.demo.entity.User;

import notes.app.demo.repository.NoteRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;


    // =====================================================
    // GET ALL NOTES
    // =====================================================

    public List<Note> getAllNotes(
            User user
    ) {

        return noteRepository
                .findByUserOrderByUpdatedAtDesc(
                        user
                );
    }


    // =====================================================
    // GET TODAY'S NOTES
    // =====================================================

    public List<Note> getTodayNotes(
            User user
    ) {

        return noteRepository
                .findByUserAndNoteDateOrderByUpdatedAtDesc(
                        user,
                        LocalDate.now()
                );
    }


    // =====================================================
    // GET ONE NOTE
    // =====================================================

    public Note getNote(
            Long id,
            User user
    ) {

        return noteRepository
                .findByIdAndUser(
                        id,
                        user
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Note not found"
                        )
                );
    }


    // =====================================================
    // CREATE NOTE
    // =====================================================

    public Note createNote(
            User user,
            String title,
            String content
    ) {

        Note note =
                new Note();


        note.setUser(
                user
        );


        if (
                title == null
                        ||
                        title.trim().isEmpty()
        ) {

            note.setTitle(
                    "Untitled Note"
            );

        } else {

            note.setTitle(
                    title.trim()
            );
        }


        if (content == null) {

            note.setContent(
                    ""
            );

        } else {

            note.setContent(
                    content
            );
        }


        return noteRepository.save(
                note
        );
    }


    // =====================================================
    // UPDATE NOTE / AUTO SAVE
    // =====================================================

    public Note updateNote(
            Long id,
            User user,
            String title,
            String content
    ) {

        Note note =
                noteRepository
                        .findByIdAndUser(
                                id,
                                user
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Note not found"
                                )
                        );


        if (
                title == null
                        ||
                        title.trim().isEmpty()
        ) {

            note.setTitle(
                    "Untitled Note"
            );

        } else {

            note.setTitle(
                    title.trim()
            );
        }


        if (content == null) {

            note.setContent(
                    ""
            );

        } else {

            note.setContent(
                    content
            );
        }


        return noteRepository.save(
                note
        );
    }


    // =====================================================
    // DELETE NOTE
    // =====================================================

    public void deleteNote(
            Long id,
            User user
    ) {

        Note note =
                noteRepository
                        .findByIdAndUser(
                                id,
                                user
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Note not found"
                                )
                        );


        noteRepository.delete(
                note
        );
    }
}