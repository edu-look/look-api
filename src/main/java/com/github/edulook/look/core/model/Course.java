package com.github.edulook.look.core.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course { 
    private String id;
    private String state;
    private String description;
    private String name;
    private String ownerId;
    private String room;
    private String section;
    private String updated;
    private List<Teacher> teachers;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Announcement {
        private String id;
        private String courseId;
        private String content;
        private String createdAt;
        private String owner;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WorkMaterial {
        private String id;
        private String courseId;
        private String title;
        private String description;
        private String createdAt;
        private List<Material> materials;

        @Builder
        public static record Material (
          String description,
          String type,
          String originLink,
          String previewLink
        ){}
    }
}
