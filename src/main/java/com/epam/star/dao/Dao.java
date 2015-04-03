package com.epam.star.dao;

import com.epam.star.dao.util.PaginatedList;
import com.epam.star.entity.AbstractEntity;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface Dao<T extends AbstractEntity> {

    T findById(int ID);

    void insert(T entity);

    void deleteEntity(T entity);

    T updateEntity(T entity);

    List<T> findAll();

    PaginatedList<T> findRangee(int pageNumber, int rowsCount, Map<String,String> fields, String entityName, String orderBy);
}
