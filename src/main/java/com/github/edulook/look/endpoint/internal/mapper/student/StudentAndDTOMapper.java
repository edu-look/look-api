package com.github.edulook.look.endpoint.internal.mapper.student;

import org.mapstruct.Mapper;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.endpoint.io.student.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentAndDTOMapper {
    StudentDTO toDto(Student source);
}
