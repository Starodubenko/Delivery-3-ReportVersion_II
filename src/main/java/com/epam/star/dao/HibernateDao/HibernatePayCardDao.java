package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PayCardDao;
import com.epam.star.entity.PayCard;

@MappedDao("PayCard")
public class HibernatePayCardDao extends AbstractHibernateDao<PayCard> implements PayCardDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public PayCard findBySerialNumber(String serNum) {
        return null;
    }

    @Override
    public PayCard findBySecretNumber(String secNum) {
        return null;
    }

    @Override
    public PayCard findByStatus(String status) {
        return null;
    }
}
