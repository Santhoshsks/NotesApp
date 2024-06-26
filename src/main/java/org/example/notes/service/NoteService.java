package org.example.notes.service;

import org.example.notes.entity.Note;

import java.time.LocalDate;
import java.util.List;

public interface NoteService {
    Note createNote(Note note);
    List<Note> getAllNotes(String sortBy, String sortDirection);
    Note getNoteById(String id);
    Note updateNote(String id, Note updatedNote);
    void deleteNoteById(String id);
    List<Note> getAllNotesByKeyword(String keyword);
    List<Note> filterNotes(String tag, LocalDate startDate, LocalDate endDate);
}
