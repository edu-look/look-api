package com.github.edulook.look.infra.repository.http;

import com.github.edulook.look.core.model.Announcement;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("CourseRepositoryHTTP::Class")
public class CourseRepositoryHTTP implements CourseRepository {

    private final GetCourse getCourse;
    private final GetCourseWorkMaterial getCourseWorkMaterial;
    private final GetCourseWork getCourseWork;
    private final GetCourseAnnouncement getCourseAnnouncement;

    public CourseRepositoryHTTP(@Qualifier("GetCourseHTTP::Class") GetCourse getCourse,
                                @Qualifier("GetCourseWorkMaterialHTTP::Class") GetCourseWorkMaterial getCourseWorkMaterial,
                                @Qualifier("GetCourseWorkHTTP::Class") GetCourseWork getCourseWork,
                                @Qualifier("GetCourseAnnouncementHTTP::Class") GetCourseAnnouncement getCourseAnnouncement) {

        this.getCourse = getCourse;
        this.getCourseWorkMaterial = getCourseWorkMaterial;
        this.getCourseWork = getCourseWork;
        this.getCourseAnnouncement = getCourseAnnouncement;
    }

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        return  getCourse.findCoursesByStudentId(studentId);
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        return getCourse.findOneCourseByStudentId(courseId, studentId);
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        return  getCourseWorkMaterial.listAllWorkMaterial(course);
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        return getCourseWorkMaterial.findOneMaterial(course, materialId);
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return getCourseWorkMaterial.listAllWorkMaterial(course, access);
    }

    @Override
    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        return getCourseAnnouncement.getAllAnnouncementByCourse(course);
    }

    @Override
    @CacheEvict(value = "findOneCourseMaterial", allEntries = true)
    public WorkMaterial upsetCourseMaterial(WorkMaterial materialSaved) {
        // TODO: add persistence here

        return materialSaved;
    }

    @Override
    public List<WorkMaterial> listAllWorks(Course course) {
        return getCourseWork.listAllWorks(course);
    }
}
