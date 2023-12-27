package com.github.edulook.look.core.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collectionName = "look_student_course")
public class CourseStudent {

    @DocumentId
    private String studentId;
    private List<String> courseId;

}
