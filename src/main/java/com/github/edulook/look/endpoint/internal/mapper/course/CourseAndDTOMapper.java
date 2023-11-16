package com.github.edulook.look.endpoint.internal.mapper.course;

import java.util.List;

import org.mapstruct.Mapper;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.endpoint.io.course.CourseDTO;

@Mapper(componentModel = "spring")
public interface CourseAndDTOMapper {
    CourseDTO toDto(Course source);
    Course toModel(CourseDTO source);
    List<CourseDTO> toDtoList(List<Course> source);
}
