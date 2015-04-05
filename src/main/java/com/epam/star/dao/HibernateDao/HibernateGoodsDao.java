package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.GoodsDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Goods;

import javax.persistence.Query;
import java.util.List;

@MappedDao("Goods")
public class HibernateGoodsDao extends AbstractHibernateDao<Goods> implements GoodsDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public Goods findByGoodsName(String name) {
        Query query = em.createQuery("select g from Goods g where lower(g.goodsName) = lower(" + name + ")");
        return (Goods) query.getSingleResult();
    }
}
