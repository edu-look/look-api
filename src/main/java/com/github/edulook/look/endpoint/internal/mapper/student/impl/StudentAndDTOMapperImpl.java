package com.github.edulook.look.endpoint.internal.mapper.student.impl;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.endpoint.internal.mapper.student.StudentAndDTOMapper;
import com.github.edulook.look.endpoint.io.student.StudentDTO;

@Component
public class StudentAndDTOMapperImpl implements StudentAndDTOMapper {

    @Override
    public StudentDTO toDto(Student source) {
        return StudentDTO.builder()
        .name(source.getProfile().name())
        .photo(source.getProfile().photoUrl())
        .email(source.getProfile().email())
        .build();
    }
}
