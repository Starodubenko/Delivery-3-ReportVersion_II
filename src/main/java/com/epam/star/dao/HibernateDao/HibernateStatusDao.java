package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.StatusDao;
import com.epam.star.entity.PayCard;
import com.epam.star.entity.Status;

import javax.persistence.Query;

@MappedDao("Status")
public class HibernateStatusDao extends AbstractHibernateDao<Status> implements StatusDao {

    @Override
    protected Class getEntityClass() {
        return Status.class;
    }

    @Override
    public Status findByStatusName(String name) {
        Query query = em.createQuery("select s from Status s where s.statusName = " + name);
        return (Status) query.getSingleResult();
    }
}
