package com.github.edulook.look.core.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    private String id;
    @Column
    private String state;
    @Column
    private String description;
    @Column
    private String name;
    @Column(name = "owner_id")
    private String ownerId;
    @Column
    private String room;
    @Column
    private String section;
    @Column
    private String updated;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Student> students;

    public Course(String courseId, String id, String description, String title, String createdAt) {}

    public Boolean isValid() {
        return getId() != null;
    }
    public Boolean isNotValid() {
        return !isValid();
    }

}
