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
    public CourseDTO toDto(Course source) {
        return CourseDTO
            .builder()
            .id(source.getId())
            .name(source.getName())
            .description(source.getDescription())
            .ownerEmail(source.getOwnerId())
            .room(source.getRoom())
            .courseState(source.getState())
            .build();
    }

    @Override
    public Course toModel(CourseDTO source) {
        throw new UnsupportedOperationException("Unimplemented method 'toModel'");
    }

    @Override
    public List<CourseDTO> toDtoList(List<Course> source) {
        return Optional
            .ofNullable(source)
            .orElse(List.of())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
