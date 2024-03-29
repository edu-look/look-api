package com.github.edulook.look.infra.worker.events.course;

import lombok.Builder;
import lombok.Getter;

import com.github.edulook.look.core.model.Announcement;

@Getter
@Builder
public class AnnouncementEvent  {

    private String id;
    private String courseId;
    private String content;
    private String createdAt;
    private String owner;


    public static AnnouncementEvent fromModel(Announcement announcement) {
        return AnnouncementEvent.builder()
            .id(announcement.getId())
            .courseId(announcement.getCourseId())
            .content(announcement.getContent())
            .createdAt(announcement.getCreatedAt())
            .owner(announcement.getOwner())
            .build();
    }
}
