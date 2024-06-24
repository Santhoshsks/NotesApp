package org.example.notes.service;

import org.example.notes.entity.Note;
import java.util.List;

public interface NoteService {
    Note createNote(Note note);
    List<Note> getAllNotes();
    Note getNoteById(String id);
    Note updateNote(String id, Note updatedNote);
    void deleteNoteById(String id);
}
