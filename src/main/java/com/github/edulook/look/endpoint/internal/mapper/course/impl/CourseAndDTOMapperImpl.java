package com.github.edulook.look.endpoint.internal.mapper.course.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;

@Component
public class CourseAndDTOMapperImpl implements CourseAndDTOMapper{

    @Override
    public CourseDTO toDto(Course course) {
        return new CourseDTO(
            course.getId(),
            course.getName(),
            course.getDescription(),
            course.getOwnerId(),
            course.getRoom(),
            course.getState()
        );
    }

    @Override
    public Course toModel(CourseDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toModel'");
    }

    @Override
    public List<CourseDTO> toDtoList(List<Course> list) {
        return Optional
            .ofNullable(list)
            .orElse(List.of())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    
}
