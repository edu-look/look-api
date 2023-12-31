package com.github.edulook.look.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private String id;
    private Profile profile;

    @Builder
    public static record Profile(
        String email,
        String name,
        String photoUrl
    ) {}
}
