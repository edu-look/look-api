package com.github.edulook.look.endpoint.internal.mapper.student.impl;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.endpoint.internal.mapper.student.StudentAndDTOMapper;
import com.github.edulook.look.endpoint.io.student.StudentDTO;

@Component
public class StudentAndDTOMapperImpl implements StudentAndDTOMapper {

    @Override
    public StudentDTO toDto(Student student) {
        return StudentDTO.builder()
        .name(student.getProfile().name())
        .photo(student.getProfile().photoUrl())
        .email(student.getProfile().email())
        .build();
    }
}
