package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PositionDao;
import com.epam.star.entity.Position;

import javax.persistence.Query;
import java.util.List;


@MappedDao("Position")
public class HibernatePositionDao extends AbstractHibernateDao<Position> implements PositionDao {

    @Override
    protected Class getEntityClass() {
        return Position.class;
    }

    @Override
    public Position findByPositionName(String name) {
        Query query = em.createQuery("select p from Position p where lower(p.positionName) = lower("+name+")");
        List<Position> resultList = query.getResultList();
        return resultList.get(0);
    }
}
