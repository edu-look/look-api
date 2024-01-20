package com.github.edulook.look.infra.repository.http.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import com.github.edulook.look.infra.repository.http.course.mapper.ClassroomMaterialToCourseMaterialMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.CourseWork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseWorkHTTP::Class")
public class GetCourseWorkHTTP implements GetCourseWork {
    private final Classroom classroom;
    private final ClassroomMaterialToCourseMaterialMapper classroomMaterialToCourseMaterialMapper;

    public GetCourseWorkHTTP(Classroom classroom, ClassroomMaterialToCourseMaterialMapper classroomMaterialToCourseMaterialMapper) {
        this.classroom = classroom;
        this.classroomMaterialToCourseMaterialMapper = classroomMaterialToCourseMaterialMapper;
    }

    @Override
    public List<WorkMaterial> listAllWorks(Course course) {
        var courseWorks =  getCoursesWorks(course);
        if(courseWorks.isEmpty())
            return List.of();
        return courseWorks
            .map(classroomMaterialToCourseMaterialMapper::toModel)
            .orElseGet(List::of);
    }

    private Optional<List<CourseWork>> getCoursesWorks(Course course) {
        try {
            var courseWorks = classroom.courses()
                .courseWork()
                .list(course.getId())
                .execute();
            if(courseWorks == null || courseWorks.isEmpty())
                return Optional.empty();
            return Optional.of(courseWorks.getCourseWork());
        } catch (IOException e) {
            log.error("error:: ", e);
            return Optional.empty();
        }
    }
}
