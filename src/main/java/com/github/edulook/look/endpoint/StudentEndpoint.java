package com.github.edulook.look.endpoint;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public StudentDTO getProfile(Principal principal) {
            var user = principal.getName();

            log.info("user logged: {}", user);

            var student = studentService
                .getStudentProfile(user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("profile to %s not found", user)));

            return mapper.toDto(student);
    }
}
