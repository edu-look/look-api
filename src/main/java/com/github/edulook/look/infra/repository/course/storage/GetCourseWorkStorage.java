package com.github.edulook.look.infra.repository.course.storage;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("GetCourseWorkStorage:Class")
public class GetCourseWorkStorage implements GetCourseWork {
    @Override
    public List<Course.WorkMaterial> listAllWorks(Course course) {
        return List.of();
    }
}
