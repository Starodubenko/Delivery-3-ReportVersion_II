package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.util.PaginatedList;
import com.epam.star.entity.AbstractEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Stateless
@Named
public abstract class AbstractHibernateDao<T extends AbstractEntity> implements Serializable {

    @Inject
    protected EntityManager em;

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
