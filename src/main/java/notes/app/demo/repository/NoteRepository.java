//package notes.app.demo.repository;
//
////package com.notesapp.notes.repository;
//
////package notes.app.demo.repository;
//
//import notes.app.demo.entity.Note;
//import notes.app.demo.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface NoteRepository extends JpaRepository<Note, Long> {
//
//    List<Note> findByUserOrderByUpdatedAtDesc(User user);
//
//    List<Note> findByUserAndNoteDateOrderByUpdatedAtDesc(
//            User user,
//            LocalDate noteDate
//    );
//
//    Optional<Note> findByIdAndUser(Long id, User user);
//}
//
//package notes.app.demo.repository;
//
//import notes.app.demo.entity.Note;
//import notes.app.demo.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface NoteRepository extends JpaRepository<Note, Long> {
//
//    List<Note> findByUserOrderByUpdatedAtDesc(User user);
//
//    List<Note> findByUserAndNoteDateOrderByUpdatedAtDesc(
//            User user,
//            LocalDate noteDate
//    );
//
//    Optional<Note> findByIdAndUser(
//            Long id,
//            User user
//    );
//}

package notes.app.demo.repository;

import notes.app.demo.entity.Note;
import notes.app.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NoteRepository
        extends JpaRepository<Note, Long> {

    List<Note> findByUserOrderByUpdatedAtDesc(
            User user
    );


    List<Note> findByUserAndNoteDateOrderByUpdatedAtDesc(
            User user,
            LocalDate noteDate
    );


    Optional<Note> findByIdAndUser(
            Long id,
            User user
    );
}