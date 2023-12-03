package com.github.edulook.look.core.repository.course;

import java.util.List;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;

public interface GetCourseAnnouncement {
    List<Announcement> getAllAnnouncementByCourse(Course course);
}
