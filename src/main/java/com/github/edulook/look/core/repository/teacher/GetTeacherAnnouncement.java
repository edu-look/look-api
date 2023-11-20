package com.github.edulook.look.core.repository.teacher;

import java.util.List;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;

public interface GetTeacherAnnouncement {
    List<Announcement> getAllAnnouncementByCourse(Course course);
}
