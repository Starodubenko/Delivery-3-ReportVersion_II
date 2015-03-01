package com.epam.star.dao;

import com.epam.star.entity.Order;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    List<Order> findAllByClientIdToday(int id);

    List<Order> findAllByClientIdLastDays(int id);

    List<Order> findAllByClientId(int clientId);

    int getClientOrdersCount(int id);

    String changeStatus(Order order);
}
