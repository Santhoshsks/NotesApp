package org.example.notes.service.impl;

import lombok.AllArgsConstructor;
import org.example.notes.entity.Note;
import org.example.notes.exception.ResourceNotFoundException;
import org.example.notes.repository.NotesRepository;
import org.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    @Autowired
    private final NotesRepository notesRepository;

    @Override
    public Note createNote(Note note) {
        return notesRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes() {
        return notesRepository.findAll();
    }

    @Override
    public Note getNoteById(String id) {
        return notesRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Note does not exist with id: " + id));
    }

    @Override
    public Note updateNote(String id, Note updatedNote) {
        Note note =  notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        return notesRepository.save(note);
    }

    @Override
    public void deleteNoteById(String id) {
        notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));

        notesRepository.deleteById(id);
    }
}