package com.github.edulook.look.core.model;


import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    private String id;
    @Column
    private String state;
    @Column
    private String description;
    @Column
    private String name;
    @Column(name = "owner_id")
    private String ownerId;
    @Column
    private String room;
    @Column
    private String section;
    @Column
    private String updated;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teachers;

    public Course(String courseId, String id, String description, String title, String createdAt) {}

    public Boolean isValid() {
        return getId() != null;
    }
    public Boolean isNotValid() {
        return !isValid();
    }

    @Entity
    @Table(name = "announcements")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Announcement {
        @Id
        private String id;
        @Column(name = "course_id")
        private String courseId;
        @Column
        private String content;
        @Column
        private String createdAt;
        @Column
        private String owner;
    }

    @Entity
    @Table(name = "work_materials")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WorkMaterial {
        @Id
        private String id;
        @Column(name = "course_id")
        private String courseId;
        @Column
        private String title;
        @Column
        private String description;
        @Column
        private String createdAt;
        @OneToMany(mappedBy = "workMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Material> materials;

        @Entity
        @Table(name = "material")
        @Builder
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Material {
            @Id
            private String id;
            @Column
            private String name;
            @Column
            private String description;
            @Column
            private String type;
            @Column
            private String originLink;
            @Column
            private String previewLink;

            private PageContent content;

            private Option option;

            public Optional<PageContent> getContent() {
                return Optional.ofNullable(content);
            }

            public void setContent(Optional<PageContent> content) {
                this.content = content.orElse(null);
            }

            public Optional<Option> getOption() {
                return Optional.ofNullable(option);
            }

            public void setOption(Optional<Option> option) {
                this.option = option.orElse(null);
            }

        }
    }
}
