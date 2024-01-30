package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teachers")
@Getter
@Setter
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
