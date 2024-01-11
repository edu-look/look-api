package com.github.edulook.look.core.model;


import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.PageContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    private String id;
    @Column(nullable = false)
    private String state;
    @Column
    private String description;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String ownerId;
    @Column
    private String room;
    @Column
    private String section;
    @Column
    private String updated;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkMaterial> workMaterials;

    public Course(String courseId, String id, String description, String title, String createdAt) {
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Entity
    @Table(name = "announcement")
    public static class Announcement {
        @Id
        private String id;
        @ManyToOne
        @JoinColumn(name = "course_id")
        private String courseId;
        @Column
        private String content;
        @Column
        private String createdAt;
        @Column
        private String owner;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Entity
    @Table(name = "work_material")
    public static class WorkMaterial {
        @Id
        private String id;
        @ManyToOne
        @JoinColumn(name = "course_id")
        private String courseId;
        @Column(nullable = false)
        private String title;
        @Column(nullable = false)
        private String description;
        @Column
        private String createdAt;
        @OneToMany(mappedBy = "workMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Material> materials;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Entity
        @Table(name = "material")
        public static class Material {
            @Id
            private String id;
            @Column(nullable = false)
            private String name;
            @Column
            private String description;
            @Column
            private String type;
            @Column
            private String originLink;
            @Column
            private String previewLink;

            @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
            private Optional<PageContent> content;

            @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
            private Optional<Option> option;
        }
    }
}
