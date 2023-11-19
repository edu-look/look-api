package com.github.edulook.look.service;

import org.springframework.stereotype.Service;

import com.github.edulook.look.core.usecases.teacher.GetTeacher;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
/**
 * Facade teacher service 
 */
public class TeacherService {
    private final GetTeacher getTeacher;
}
