package com.github.edulook.look.endpoint;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.edulook.look.endpoint.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.internal.mapper.student.StudentAndDTOMapper;
import com.github.edulook.look.endpoint.io.student.StudentDTO;
import com.github.edulook.look.service.student.StudentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("v1/students")
public class StudentEndpoint {

    private final StudentService studentService;
    private final StudentAndDTOMapper mapper;
    
    @GetMapping("profile")
    public StudentDTO getProfile(@RequestParam(name = "id") String userLogged) {
        log.debug("student logged: {}", userLogged);
        
        var student = studentService
            .getStudentProfile(userLogged)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("profile to %s not found", userLogged)));

        return mapper.toDto(student);
    }
}
