package com.example.todojdbc.repository;

import java.util.Collection;

public interface CommonRepository<T> {

    T save(T toDo);

    Iterable<T> save(Collection<T> toDo);

    void delete(T toDo);

    T findById(String id);

    Iterable<T> findAll();
}
