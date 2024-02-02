package com.github.edulook.look.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {
    @Id
    private String id;
    @Column(name = "course_id")
    private String courseId;
    @Column
    private String content;
    @Column
    private String createdAt;
    @Column
    private String owner;
}
