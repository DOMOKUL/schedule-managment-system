package com.company.schedule.managment.system.dao;

import java.util.List;

public interface CrudDao<T> {

    T create(T t);

    T findById(Integer id);

    List<T> findAll();

    boolean update(T t);

    boolean delete(Integer id);
}
