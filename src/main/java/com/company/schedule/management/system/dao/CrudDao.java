package com.company.schedule.management.system.dao;

import java.util.List;

public interface CrudDao<T> {

    T create(T t);

    T findById(Long id);

    List<T> findAll();

    boolean update(T t);

    boolean delete(Long id);
}
