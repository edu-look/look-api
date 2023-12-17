package com.github.edulook.look.endpoint.internal.mapper.course.impl;

import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.course.CourseDTO.AnnouncementDTO;
import com.github.edulook.look.endpoint.io.course.CourseDTO.TeacherDTO;
import com.github.edulook.look.endpoint.io.course.MaterialDTO;
import com.github.edulook.look.endpoint.io.course.MaterialDTO.ContentMaterialDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class CourseAndDTOMapperImpl implements CourseAndDTOMapper {

    @Override
    public CourseDTO toDTO(Course course) {
        return CourseDTO
            .builder()
            .id(course.getId())
            .description(course.getDescription())
            .name( course.getName())
            .owner(course.getOwnerId())
            .room(course.getRoom())
            .state(course.getState())
            .teachers(toTeacherDTOList(course.getTeachers()))
            .build();
    }

    private TeacherDTO toDTO(Teacher source) {
        return TeacherDTO
            .builder()
            .id(source.getId())
            .name(source.getName())
            .email(source.getEmail())
            .photo(source.getPhoto())
            .build();
    }

    private List<TeacherDTO> toTeacherDTOList(List<Teacher> source) {
        return source
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public AnnouncementDTO toModel(Announcement source) {
        return AnnouncementDTO
            .builder()
            .id(source.getId())
            .content(source.getContent())
            .createdAt(source.getCreatedAt())
            .owner(source.getOwner())
            .build();
    }

    @Override
    public Course toModel(CourseDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toModel'");
    }

    @Override
    public List<CourseDTO> toDTOList(List<Course> source) {
        return Optional
            .ofNullable(source)
            .orElse(List.of())
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public MaterialDTO toDTO(WorkMaterial source) {

        var materials = source
            .getMaterials()
            .parallelStream()
            .map(this::toDTO)
            .toList();

        return MaterialDTO.builder()
            .description(source.getDescription())
            .materials(materials)
            .build();
    }

    @Override
    public ContentMaterialDTO toDTO(WorkMaterial.Material source) {
        var range = source.getType().equalsIgnoreCase(Typename.PDF)
            ? Range.withDefaults()
            : Range.None();

        return ContentMaterialDTO.builder()
            .description(source.getDescription())
            .id(source.getId())
            .name(source.getName())
            .origin(source.getOriginLink())
            .preview(source.getPreviewLink())
            .range(range)
            .build();
    }
}
