package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PositionDao;
import com.epam.star.entity.Position;


@MappedDao("Position")
public class HibernatePositionDao extends AbstractHibernateDao<Position> implements PositionDao {

    @Override
    protected Class getEntityClass() {
        return Position.class;
    }

    @Override
    public Position findByPositionName(String name) {
        return null;
    }
}
