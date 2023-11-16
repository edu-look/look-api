package com.github.edulook.look.endpoint.io.student;

import lombok.Builder;

@Builder
public record StudentDTO(
    String name, 
    String email,
    String photo
) {
    
}
