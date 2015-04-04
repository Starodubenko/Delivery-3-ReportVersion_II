package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PayCardStatusDao;
import com.epam.star.entity.StatusPayCard;

@MappedDao("StatusPayCard")
public class HibernatePayCardStatusDao extends AbstractHibernateDao<StatusPayCard> implements PayCardStatusDao {

    @Override
    protected Class getEntityClass() {
        return StatusPayCard.class;
    }

    @Override
    public StatusPayCard findByStatusName(String name) {
        return null;
    }
}
