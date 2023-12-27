package com.github.edulook.look.infra.repository.firestore;

import com.github.edulook.look.core.model.Course;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

public interface WorkMaterialFirestoreRepository extends FirestoreReactiveRepository<Course.WorkMaterial> {

}
