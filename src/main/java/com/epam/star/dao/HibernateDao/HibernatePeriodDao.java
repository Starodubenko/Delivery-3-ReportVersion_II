package com.epam.star.dao.HibernateDao;


import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PeriodDao;
import com.epam.star.entity.Period;
import java.sql.Time;


@MappedDao("Period")
public class HibernatePeriodDao extends AbstractHibernateDao<Period> implements PeriodDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public Period findByPeriod(Time period) {
        return null;
    }
}
