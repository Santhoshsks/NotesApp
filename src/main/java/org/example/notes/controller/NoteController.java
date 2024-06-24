package org.example.notes.controller;

import org.example.notes.dto.NoteDto;
import org.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        NoteDto savedNote = noteService.createNote(noteDto);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") String noteId) {
        NoteDto noteDto = noteService.getNoteById(noteId);
        return new ResponseEntity<>(noteDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        List<NoteDto> noteDtos = noteService.getAllNotes();
        return new ResponseEntity<>(noteDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable("id") String noteId,
                                              @RequestBody NoteDto updateNoteDto) {
        NoteDto updatedNote = noteService.updateNote(noteId, updateNoteDto);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") String noteId) {
        noteService.deleteNoteById(noteId);
        return new ResponseEntity<>("Note deleted", HttpStatus.OK);
    }
}
