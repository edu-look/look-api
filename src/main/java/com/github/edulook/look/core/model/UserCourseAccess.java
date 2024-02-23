package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_course_access")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourseAccess {
    @Id
    private Long id;
    private String access;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAuth user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
