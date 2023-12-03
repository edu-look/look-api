package com.github.edulook.look.infra.repository.course;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.ListCourseWorkMaterialResponse;

import lombok.extern.slf4j.Slf4j;

import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;
import static com.github.edulook.look.infra.repository.course.mapper.ClassroomMaterialToCourseMaterialMapper.*;

@Slf4j
@Component("GetCourseWorkMaterial::Class")
public class GetCourseWorkMaterialImpl implements GetCourseWorkMaterial {

    @Autowired
    private Classroom classroom;

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        return listAllWorkMaterial(course, null);
    }
    

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        try {
            var materials = getAllWorkMaterialFromClassroom(course, access);
            if(materials.isEmpty()) {
                return List.of();
            }

            return toWorkMaterialCore(materials.get());

        } catch (IOException e) {
            e.printStackTrace();
            log.error("list work material error: {}", e);
        }
    
        return List.of();
    }


    private Optional<ListCourseWorkMaterialResponse> getAllWorkMaterialFromClassroom(Course course, String access) throws IOException {
        var client = classroom.courses()
            .courseWorkMaterials()
            .list(course.getId());
        
        client =  (access == null || access.isBlank())
            ? client
            : client.setAccessToken(access);

        return Optional.ofNullable(client.execute());
    }

    private List<WorkMaterial> toWorkMaterialCore(ListCourseWorkMaterialResponse materials) {
        var workMaterial = Optional.ofNullable(materials.getCourseWorkMaterial());
        
        if(workMaterial.isEmpty()) {
            return List.of();
        }

        return workMaterial.get()
            .parallelStream()
            .map(it -> {

                var materialsCore = it.getMaterials()
                    .parallelStream()
                    .map(this::buildCoreMaterial)
                    .toList();

                return WorkMaterial
                    .builder()
                    .createdAt(it.getCreationTime())
                    .title(it.getTitle())
                    .id(it.getId())
                    .description(it.getDescription())
                    .materials(materialsCore)
                    .build();
            })
            .toList();
    }

    private Material buildCoreMaterial(com.google.api.services.classroom.model.Material material) {
        if(material.getYoutubeVideo() != null)
            return fromYoutubeResource(material);
        if(material.getDriveFile() != null)
            return fromDriveResource(material);
        if(material.getForm() != null)
            return fromFormResource(material);

        return Material.builder().build();
    }


}
