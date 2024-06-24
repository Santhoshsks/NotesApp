package org.example.notes.repository;

import org.example.notes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note, String> {

}
