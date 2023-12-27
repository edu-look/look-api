package com.github.edulook.look.infra.repository.firestore;

import com.github.edulook.look.core.model.Course;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CourseFirestoreRepository extends FirestoreReactiveRepository<Course> {

    Mono<Course> findById(String id);
}
