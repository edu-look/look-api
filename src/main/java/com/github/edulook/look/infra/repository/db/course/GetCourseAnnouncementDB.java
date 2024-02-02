package com.github.edulook.look.infra.repository.db.course;

import com.github.edulook.look.core.model.Announcement;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("GetCourseAnnouncementDB::Class")
public class GetCourseAnnouncementDB implements GetCourseAnnouncement {
    @Override
    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        return List.of();
    }
}
