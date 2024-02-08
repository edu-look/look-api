package com.github.edulook.look.infra.repository.db.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("GetCourseWorkDB:Class")
public class GetCourseWorkDB implements GetCourseWork {
    @Override
    public List<WorkMaterial> listAllWorks(Course course) {
        return List.of();
    }
}
