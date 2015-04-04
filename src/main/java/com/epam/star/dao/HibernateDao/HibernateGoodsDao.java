package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.GoodsDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Goods;

@MappedDao("Goods")
public class HibernateGoodsDao extends AbstractHibernateDao<Goods> implements GoodsDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public Goods findByGoodsName(String name) {
        return null;
    }
}
