package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PayCardStatusDao;
import com.epam.star.entity.PayCard;
import com.epam.star.entity.StatusPayCard;

import javax.persistence.Query;

@MappedDao("StatusPayCard")
public class HibernatePayCardStatusDao extends AbstractHibernateDao<StatusPayCard> implements PayCardStatusDao {

    @Override
    protected Class getEntityClass() {
        return StatusPayCard.class;
    }

    @Override
    public StatusPayCard findByStatusName(String name) {
        Query query = em.createQuery("select p from StatusPayCard p where p.statusName = " + name);
        return (StatusPayCard) query.getSingleResult();
    }
}
