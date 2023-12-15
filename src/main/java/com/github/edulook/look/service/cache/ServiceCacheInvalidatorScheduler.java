package com.github.edulook.look.service.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Configuration
@EnableScheduling
public class ServiceCacheInvalidatorScheduler {

    @Component
    @Slf4j
    public static class CourseServiceCache {

        @CacheEvict(value = "listCourses", allEntries = true)
        @Scheduled(fixedRateString = "${look.application.caching.tll}")
        public void cleanListCoursesCache() throws IOException {
            log.info("Invalided cache listCourses");
        }

        @CacheEvict(value = "listAllWorkMaterials", allEntries = true)
        @Scheduled(fixedRateString = "${look.application.caching.tll}")
        public void cleanListAllWorkMaterialsCache() {
            log.info("Invalided cache listAllWorkMaterials");
        }
        @CacheEvict(value = "listAllAnnouncements", allEntries = true)
        @Scheduled(fixedRateString = "${look.application.caching.tll}")
        public void cleanListAllAnnouncementsCache() {
            log.info("Invalided cache listAllAnnouncements");
        }
    }

    @Component
    @Slf4j
    public static class StudentServiceCache {
        @CacheEvict(value = "getStudentProfile", allEntries = true)
        @Scheduled(fixedRateString = "${look.application.caching.tll}")
        public void cleanGetStudentProfileCache() {
            log.info("Invalided cache getStudentProfile");
        }
    }
}
