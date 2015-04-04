package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.DiscountDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Discount;

@MappedDao("Discount")
public class HibernateDiscountDao extends AbstractHibernateDao<Discount> implements DiscountDao {

    @Override
    protected Class getEntityClass() {
        return Discount.class;
    }

    @Override
    public Discount findByName(String name) {
        return null;
    }

    @Override
    public Discount findByPercentage(Integer discount) {
        return null;
    }
}
