package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.util.PaginatedList;
import com.epam.star.entity.AbstractEntity;
import com.epam.star.entity.Client;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public abstract class AbstractHibernateDao<T extends AbstractEntity> {

    @Inject
    protected EntityManager em;

    public AbstractHibernateDao() {
    }

    public T findById(int id){
        return (T)em.find(getEntityClass(), id);
    }

    @Transactional
    public void insert(T entity) {
        em.persist(entity);
    }

    @Transactional
    public void deleteEntity(T entity) {
        em.remove(entity);
    }

    @Transactional
    public T updateEntity(T entity) {
        return em.merge(entity);
    }

    public List<T> findAll() {
        return null;
    }

    public PaginatedList<T> findRangee(int pageNumber, int rowsCount, Map<String, String> fields, String entityName, String orderBy) {
        return null;
    }

    protected abstract Class getEntityClass();
}
