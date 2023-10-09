package com.github.edulook.look.services;

import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CourseService {

    private final Classroom classroom;

    @Autowired
    public CourseService(Classroom classroom) {
        this.classroom = classroom;
    }

    public Course createCourse(String name, String description) throws IOException {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);

        return classroom.courses().create(course).execute();
    }

    public List<Course> listCourses() throws IOException {
        Classroom.Courses.List listRequest = classroom.courses().list();
        listRequest.setPageSize(5); // Defina o tamanho da página conforme necessário.

        return listRequest.execute().getCourses();
    }

}
