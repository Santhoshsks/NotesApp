package org.example.notes.service.impl;

import lombok.AllArgsConstructor;
import org.example.notes.dto.NoteDto;
import org.example.notes.entity.Note;
import org.example.notes.exception.ResourceNotFoundException;
import org.example.notes.mapper.NoteMapper;
import org.example.notes.repository.NotesRepository;
import org.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    @Autowired
    private final NotesRepository notesRepository;

    @Override
    public NoteDto createNote(NoteDto noteDto) {
        Note note = NoteMapper.mapToNote(noteDto);
        Note savedNote = notesRepository.save(note);
        return NoteMapper.mapToNoteDto(savedNote);
    }

    @Override
    public List<NoteDto> getAllNotes() {
        List<Note> notes = notesRepository.findAll();
        return notes.stream().map(NoteMapper::mapToNoteDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(String id) {
        Note note =  notesRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Note does not exist with id: " + id));
        return NoteMapper.mapToNoteDto(note);
    }

    @Override
    public NoteDto updateNote(String id, NoteDto updatedNoteDto) {
        Note note =  notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));

        note.setTitle(updatedNoteDto.getTitle());
        note.setContent(updatedNoteDto.getContent());
        Note savedNote = notesRepository.save(note);
        return NoteMapper.mapToNoteDto(savedNote);
    }

    @Override
    public void deleteNoteById(String id) {
        Note note =  notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note does not exist with id: " + id));

        notesRepository.deleteById(id);
    }
}