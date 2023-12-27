package com.github.edulook.look.infra.repository.firestore;

import com.github.edulook.look.core.model.CourseStudent;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentFirestoreRepository extends FirestoreReactiveRepository<CourseStudent> {

}
