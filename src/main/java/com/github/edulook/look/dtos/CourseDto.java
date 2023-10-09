package com.github.edulook.look.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDto {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String ownerEmail;
    private String room;
    private String courseState;


}
