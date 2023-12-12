package com.github.edulook.look.infra.repository.course;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.github.edulook.look.infra.repository.course.mapper.ClassroomMaterialToCourseMaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.ListCourseWorkMaterialResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GetCourseWorkMaterial::Class")
public class GetCourseWorkMaterialImpl implements GetCourseWorkMaterial {

    @Autowired
    private Classroom classroom;

    @Autowired
    ClassroomMaterialToCourseMaterialMapper classroomMaterialToCourseMaterialMapper;

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
                    .stream()
                    .map(classroomMaterialToCourseMaterialMapper::toModel)
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
}
