package com.github.edulook.look.endpoint;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.internal.mapper.student.StudentAndDTOMapper;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.endpoint.io.student.StudentDTO;
import com.github.edulook.look.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/students")
public class StudentEndpoint {

    private final StudentService studentService;
    private final StudentAndDTOMapper mapper;


    public StudentEndpoint(StudentService studentService, StudentAndDTOMapper mapper) {
        this.studentService = studentService;
        this.mapper = mapper;
    }

    @GetMapping("profile")
    public StudentDTO getProfile(@RequestAttribute("user") UserAuthDTO user) {

            log.info("user logged: {}", user.id());

            var student = studentService
                .getStudentProfile(user.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("profile to %s not found", user.email())));

            return mapper.toDTO(student);
    }
}
