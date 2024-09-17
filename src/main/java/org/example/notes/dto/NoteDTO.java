package org.example.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private String id;
    private String title;
    private String content;
    private boolean archived;
    private boolean deleted;
    private boolean pinned;
    private List<String> tags;
    private String color;
    private Date createdAt;
    private Date updatedAt;
}