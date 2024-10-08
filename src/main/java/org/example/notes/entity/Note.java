package org.example.notes.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.example.notes.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private boolean archived;
    private boolean deleted;
    private boolean pinned;

    @ElementCollection
    private List<String> tags;
    private String color;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}