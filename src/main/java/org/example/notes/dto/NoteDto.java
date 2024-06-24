package org.example.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    @Id
    private String id;

    private String title;
    private String content;
    private boolean deleted;
    private boolean pinned;
    private Date createdAt;
    private Date updatedAt;
}
