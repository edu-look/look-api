package com.github.edulook.look.core.repository.course;


import java.util.Optional;

public interface Crud<T>{

    Optional<T> save(T model);

    Optional<T> delete(String id);

}
