package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "work_materials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkMaterial {
    @Id
    private String id;
    @Column(name = "course_id")
    private String courseId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String createdAt;
    @OneToMany(mappedBy = "workMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materials;
    @ManyToOne
    @JoinColumn(name = "access", referencedColumnName = "access")
    private UserCourseAccess access;

}
