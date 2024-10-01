package com.example.boubyan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
public class CourseRequest {
    String courseName;

}
