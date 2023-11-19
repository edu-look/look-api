package com.github.edulook.look.endpoint.internal.mapper.course.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.course.CourseDTO.TeacherDTO;

@Component
public class CourseAndDTOMapperImpl implements CourseAndDTOMapper{

    @Override
    public CourseDTO toDTO(Course course) {
        return CourseDTO
            .builder()
            .id(course.getId())
            .description(course.getDescription())
            .name( course.getName())
            .owner(course.getOwnerId())
            .room(course.getRoom())
            .state(course.getState())
            .teachers(toTeacherDTOList(course.getTeachers()))
            .build();
    }

    private TeacherDTO toDTO(Teacher source) {
        return TeacherDTO
            .builder()
            .id(source.getId())
            .name(source.getName())
            .email(source.getEmail())
            .photo(source.getPhoto())
            .build();
    }

    private List<TeacherDTO> toTeacherDTOList(List<Teacher> source) {
        return source
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public Course toModel(CourseDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toModel'");
    }

    @Override
    public List<CourseDTO> toDTOList(List<Course> source) {
        return Optional
            .ofNullable(source)
            .orElse(List.of())
            .stream()
            .map(this::toDTO)
            .toList();
    }

    
}
