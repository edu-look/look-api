package com.github.edulook.look.core.model;


import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collectionName = "look_courses")
public class Course {
    @DocumentId
    private String id;
    private String state;
    private String description;
    private String name;
    private String ownerId;
    private String room;
    private String section;
    private String updated;
    private List<Teacher> teachers;

    public Course(String courseId, String id, String description, String title, String createdAt) {
    }

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
    @Document(collectionName = "look_course_material")
    public static class WorkMaterial {
        @DocumentId
        private String id;
        private String courseId;
        private String title;
        private String description;
        private String createdAt;
        private List<Material> materials;

        @Builder
        @Getter
        @Setter
        public static class Material {
            private String id;
            private String name;
            private String description;
            private String type;
            private String originLink;
            private String previewLink;
            private Optional<PageContent> content;
            private Optional<Range> range;
        }
    }
}
