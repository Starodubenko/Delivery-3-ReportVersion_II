package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.DiscountDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Discount;

import javax.persistence.Query;
import java.util.List;

@MappedDao("Discount")
public class HibernateDiscountDao extends AbstractHibernateDao<Discount> implements DiscountDao {

    @Override
    protected Class getEntityClass() {
        return Discount.class;
    }

    @Override
    public Discount findByName(String name) {
        Query query = em.createQuery("select d from Discount d where lower(d.name) = lower("+name+")");
        return (Discount) query.getSingleResult();
    }

    @Override
    public Discount findByPercentage(Integer discount) {
        Query query = em.createQuery("select d from Discount d where d.percentage = " + discount);
        return (Discount) query.getSingleResult();
    }
}
