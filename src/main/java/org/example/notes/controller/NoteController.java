package org.example.notes.controller;

import org.example.notes.entity.Note;
import org.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note savedNote = noteService.createNote(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String noteId) {
        Note note = noteService.getNoteById(noteId);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false, defaultValue = "updatedAt") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "desc") String sortDirection) {
        List<Note> notes = noteService.getAllNotes(sortBy, sortDirection);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") String noteId,
                                              @RequestBody Note updateNote) {
        Note updatedNote = noteService.updateNote(noteId, updateNote);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") String noteId) {
        noteService.deleteNoteById(noteId);
        return new ResponseEntity<>("Note deleted", HttpStatus.OK);
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<List<Note>> getAllNotesByKeyword(@PathVariable("keyword") String keyword) {
        List<Note> notes = noteService.getAllNotesByKeyword(keyword);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Note>> filterNotes(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Note> notes = noteService.filterNotes(tag, startDate, endDate);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}