package org.example.notes.repository;

import org.example.notes.entity.Note;
import org.example.notes.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<Note, String> {

        @Query("SELECT n FROM Note n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%')) AND n.user = :user")
        List<Note> findAllByUserAndKeyword(@Param("user") User user, @Param("keyword") String keyword);

        @Query("SELECT n FROM Note n WHERE (:tag IS NULL OR :tag MEMBER OF n.tags) AND (:startDate IS NULL OR n.createdAt >= :startDate) AND (:endDate IS NULL OR n.createdAt <= :endDate) AND n.user = :user")
        List<Note> filterNotesByUser(@Param("user") User user,
                                     @Param("tag") String tag,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

        List<Note> findByUser(User user, Sort sort);

        Optional<Note> findByIdAndUser(String id, User user);
}
