package com.epam.star.dao.HibernateDao;


import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PeriodDao;
import com.epam.star.entity.PayCard;
import com.epam.star.entity.Period;

import javax.persistence.Query;
import java.sql.Time;


@MappedDao("Period")
public class HibernatePeriodDao extends AbstractHibernateDao<Period> implements PeriodDao {

    @Override
    protected Class getEntityClass() {
        return Period.class;
    }

    @Override
    public Period findByPeriod(Time period) {
        Query query = em.createQuery("select p from Period p where p.period = " + period);
        return (Period) query.getSingleResult();
    }
}
