package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    private String id;
    @Column
    private String email;
    @Column
    private String photo;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
