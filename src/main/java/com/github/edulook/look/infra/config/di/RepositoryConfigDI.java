package com.github.edulook.look.infra.config.di;

import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.StudentRepository;
import com.github.edulook.look.core.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RepositoryConfigDI {
    @Autowired
    @Qualifier("CourseRepositoryAdapter::Class")
    private CourseRepository courseRepository;

    @Autowired
    @Qualifier("StudentRepositoryAdapter::Class")
    private StudentRepository studentRepository;

    @Autowired
    @Qualifier("TeacherRepositoryAdapter::Class")
    private TeacherRepository teacherRepository;

    @Bean
    @Primary
    CourseRepository courseRepositoryConfig() {
        return courseRepository;
    }

    @Bean
    @Primary
    StudentRepository studentRepositoryConfig() {
        return studentRepository;
    }

    @Bean
    @Primary
    TeacherRepository teacherRepositoryConfig() {
        return teacherRepository;
    }
}
