package com.github.edulook.look.endpoint.internal.mapper.course;

import java.util.List;

import com.github.edulook.look.endpoint.io.course.MaterialDTO;
import com.github.edulook.look.endpoint.io.course.MaterialDTO.ContentMaterialDTO;

import org.mapstruct.Mapper;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.course.CourseDTO.AnnouncementDTO;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.model.Course.WorkMaterial.Material;

@Mapper(componentModel = "spring")
public interface CourseAndDTOMapper {
    CourseDTO toDTO(Course source);
    Course toModel(CourseDTO source);
    AnnouncementDTO toModel(Announcement source);
    List<CourseDTO> toDTOList(List<Course> source);
    MaterialDTO toDTO(WorkMaterial source);
    ContentMaterialDTO toDTO(Material source);
}
