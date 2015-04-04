package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.OrderDao;
import com.epam.star.entity.Order;

import java.util.List;

@MappedDao("Order")
public class HibernateOrderDao extends AbstractHibernateDao<Order> implements OrderDao {

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }

    @Override
    public List<Order> findAllByClientIdToday(int id) {
        return null;
    }

    @Override
    public List<Order> findAllByClientIdLastDays(int id) {
        return null;
    }

    @Override
    public List<Order> findAllByClientId(int clientId) {
        return null;
    }

    @Override
    public int getClientOrdersCount(int id) {
        return 0;
    }
}
