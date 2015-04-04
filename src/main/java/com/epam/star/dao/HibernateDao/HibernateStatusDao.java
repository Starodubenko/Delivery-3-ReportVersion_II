package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.StatusDao;
import com.epam.star.entity.Status;
@MappedDao("Status")
public class HibernateStatusDao extends AbstractHibernateDao<Status> implements StatusDao {

    @Override
    protected Class getEntityClass() {
        return Status.class;
    }

    @Override
    public Status findByStatusName(String name) {
        return null;
    }
}
