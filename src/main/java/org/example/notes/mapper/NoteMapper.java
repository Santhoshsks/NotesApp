package org.example.notes.mapper;

import org.example.notes.entity.Note;
import org.example.notes.dto.NoteDto;

public class NoteMapper {

    public static NoteDto mapToNoteDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.isDeleted(),
                note.isPinned(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

    public static Note mapToNote(NoteDto noteDto) {
        return new Note(
                noteDto.getId(),
                noteDto.getTitle(),
                noteDto.getContent(),
                noteDto.isDeleted(),
                noteDto.isPinned(),
                noteDto.getCreatedAt(),
                noteDto.getUpdatedAt()
        );
    }
}
