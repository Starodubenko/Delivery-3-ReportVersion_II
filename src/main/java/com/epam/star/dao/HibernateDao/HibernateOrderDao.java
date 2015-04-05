package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.H2dao.DaoException;
import com.epam.star.dao.MappedDao;
import com.epam.star.dao.OrderDao;
import com.epam.star.entity.Image;
import com.epam.star.entity.Order;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@MappedDao("Order")
public class HibernateOrderDao extends AbstractHibernateDao<Order> implements OrderDao {

    @Override
    protected Class getEntityClass() {
        return Order.class;
    }

    @Override
    public List<Order> findAllByClientIdToday(int id) {
        Query query = em.createQuery("select o from Order o where o.user.id = "+id+"" +
                " and o.orderDate = CURRENT_DATE AND not o.deleted ORDER BY o.orderDate DESC, o.id DESC");
        return query.getResultList();
    }

    @Override
    public List<Order> findAllByClientIdLastDays(int id) {
        Query query = em.createQuery("select o from Order o where o.user.id = " + id + "" +
                " and o.orderDate != CURRENT_DATE AND not o.deleted ORDER BY o.orderDate DESC, o.id DESC");
        return query.getResultList();
    }

    @Override
    public List<Order> findAllByClientId(int id) {
        Query query = em.createQuery("select o from Order o where o.user.id = " + id + "" +
                " AND not o.deleted ORDER BY o.orderDate DESC, o.id DESC");
        return query.getResultList();
    }

    @Override
    public int getClientOrdersCount(int id) {
        Query query = em.createQuery("select count(*) from Order o where o.user.id =" + id);
        return (int)query.getSingleResult();
    }
}
