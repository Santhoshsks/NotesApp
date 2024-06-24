package org.example.notes.service;

import org.example.notes.dto.NoteDto;

import java.util.List;

public interface NoteService {
    NoteDto createNote(NoteDto noteDto);
    List<NoteDto> getAllNotes();
    NoteDto getNoteById(String id);
    NoteDto updateNote(String id, NoteDto updatedNoteDto);
    void deleteNoteById(String id);
}
