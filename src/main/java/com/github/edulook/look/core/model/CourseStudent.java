//package com.github.edulook.look.core.model;
//
//import lombok.*;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//
//import javax.persistence.*;
//import java.util.List;
//
//
//@EntityScan
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(name = "course_student")
//public class CourseStudent {
//
//    @Id
//    @Column(name = "student_id")
//    private String studentId;
////    @ElementCollection
////    @CollectionTable(name = "course_student_courses", joinColumns = @JoinColumn(name = "student_id"))
//    @Column(name = "course_id")
//    private List<String> courseId;
//
//}