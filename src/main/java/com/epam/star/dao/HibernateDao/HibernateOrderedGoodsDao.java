package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.OrderedGoodsDao;
import com.epam.star.entity.Order;
import com.epam.star.entity.OrderedGoods;

import javax.persistence.Query;
import java.util.List;

@MappedDao("OrderedGoods")
public class HibernateOrderedGoodsDao extends AbstractHibernateDao<OrderedGoods> implements OrderedGoodsDao{

    @Override
    protected Class getEntityClass() {
        return OrderedGoods.class;
    }

    @Override
    public List<OrderedGoods> findByOrderNumber(int orderNumber) {
        Query query = em.createQuery("select o from OrderedGoods o where o.order.number = "+orderNumber);
        return query.getResultList();
    }
}
