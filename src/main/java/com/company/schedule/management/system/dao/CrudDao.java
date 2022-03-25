package com.company.schedule.management.system.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    T create(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

    T update(T t);

    boolean deleteById(Long id);
}
