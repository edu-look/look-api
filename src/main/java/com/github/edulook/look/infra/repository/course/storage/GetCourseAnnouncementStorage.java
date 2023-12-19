package com.github.edulook.look.infra.repository.course.storage;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("GetCourseAnnouncementStorage::Class")
public class GetCourseAnnouncementStorage implements GetCourseAnnouncement {
    @Override
    public List<Course.Announcement> getAllAnnouncementByCourse(Course course) {
        return List.of();
    }
}
