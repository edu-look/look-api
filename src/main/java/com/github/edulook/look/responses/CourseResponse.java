package com.github.edulook.look.responses;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseResponse {

    private String id;
    private String name;
    private String description;
    private String ownerEmail;
    private String room;
    private String courseState;


}
