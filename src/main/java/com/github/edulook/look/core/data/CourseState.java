package com.github.edulook.look.core.data;

import java.util.List;

public class CourseState {
    public static final String ACTIVE = "ACTIVE";
    public static final String ARCHIVED = "ARCHIVED";
    public static final String PROVISIONED = "PROVISIONED";
    public static final String DECLINED = "DECLINED";

    public static List<String> all() {
        return List.of(
            ACTIVE, ARCHIVED, PROVISIONED, DECLINED
        );
    }
}
