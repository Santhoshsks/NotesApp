package org.example.notes.repository;

import org.example.notes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NotesRepository extends JpaRepository<Note, String> {

        @Query("SELECT n FROM Note n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        List<Note> findAll(@Param("keyword") String keyword);

        @Query("SELECT n FROM Note n WHERE (:tag IS NULL OR :tag MEMBER OF n.tags) AND (:startDate IS NULL OR n.createdAt >= :startDate) AND (:endDate IS NULL OR n.createdAt <= :endDate)")
        List<Note> filterNotes(@Param("tag") String tag,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate);
}
