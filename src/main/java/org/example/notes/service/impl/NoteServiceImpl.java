package org.example.notes.service.impl;

import lombok.AllArgsConstructor;
import org.example.notes.entity.Note;
import org.example.notes.exception.ResourceNotFoundException;
import org.example.notes.repository.NotesRepository;
import org.example.notes.service.NoteService;
import org.example.notes.user.User;
import org.example.notes.user.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NotesRepository notesRepository;
    private final UserService userService;

    @Override
    public Note createNote(Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email = user.getEmail();

        User foundUser = userService.getUserByEmail(email);
        note.setUser(foundUser);

        return notesRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes(String sortBy, String sortDirection) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        return notesRepository.findByUser(user, sort);
    }

    @Override
    public Note getNoteById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return (Note) notesRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));
    }

    @Override
    public Note updateNote(String id, Note updatedNote) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Note note = (Note) notesRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        note.setColor(updatedNote.getColor());
        note.setPinned(updatedNote.isPinned());
        note.setDeleted(updatedNote.isDeleted());

        return notesRepository.save(note);
    }

    @Override
    public void deleteNoteById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Note note = (Note) notesRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));

        notesRepository.deleteById(id);
    }

    @Override
    public List<Note> getAllNotesByKeyword(String keyword) {
        if (keyword != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            return notesRepository.findAllByUserAndKeyword(user, keyword);
        }
        return List.of();
    }

}
