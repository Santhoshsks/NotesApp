package org.example.notes.controller;

import org.example.notes.dto.NoteDTO;
import org.example.notes.entity.Note;
import org.example.notes.service.NoteService;
import org.example.notes.user.User;
import org.example.notes.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteDTO> createNote(@RequestBody Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(userEmail);

        note.setUser(user);

        Note savedNote = noteService.createNote(note);
        NoteDTO noteDTO = mapToDTO(savedNote);
        return new ResponseEntity<>(noteDTO, HttpStatus.CREATED);
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable("id") String noteId) {
        Note note = noteService.getNoteById(noteId);
        NoteDTO noteDTO = mapToDTO(note);
        return new ResponseEntity<>(noteDTO, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<NoteDTO>> getAllNotes(@RequestParam(required = false, defaultValue = "updatedAt") String sortBy,
                                                     @RequestParam(required = false, defaultValue = "desc") String sortDirection) {
        List<Note> notes = noteService.getAllNotes(sortBy, sortDirection);
        List<NoteDTO> noteDTOs = notes.stream().map(this::mapToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(noteDTOs, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable("id") String noteId,
                                              @RequestBody Note updateNote) {
        Note updatedNote = noteService.updateNote(noteId, updateNote);
        NoteDTO noteDTO = mapToDTO(updatedNote);
        return new ResponseEntity<>(noteDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteNote(@PathVariable("id") String noteId) {
        noteService.deleteNoteById(noteId);
        return new ResponseEntity<>("Note deleted", HttpStatus.OK);
    }

    @GetMapping("search/{keyword}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<NoteDTO>> getAllNotesByKeyword(@PathVariable("keyword") String keyword) {
        List<Note> notes = noteService.getAllNotesByKeyword(keyword);
        List<NoteDTO> noteDTOs = notes.stream().map(this::mapToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(noteDTOs, HttpStatus.OK);
    }


    private NoteDTO mapToDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId(note.getId());
        noteDTO.setTitle(note.getTitle());
        noteDTO.setContent(note.getContent());
        noteDTO.setDeleted(note.isDeleted());
        noteDTO.setPinned(note.isPinned());
        noteDTO.setTags(note.getTags());
        noteDTO.setColor(note.getColor());
        noteDTO.setCreatedAt(note.getCreatedAt());
        noteDTO.setUpdatedAt(note.getUpdatedAt());
        return noteDTO;
    }
}
