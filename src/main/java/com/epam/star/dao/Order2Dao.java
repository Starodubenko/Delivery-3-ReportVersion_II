package com.epam.star.dao;

import com.epam.star.entity.Order;

import java.util.List;

public interface Order2Dao extends Dao<Order> {
    List<Order> findAllByClientIdToday(int id);

    List<Order> findAllByClientIdLastDays(int id);

    List<Order> findAllByClientId(int clientId);

    int getClientOrdersCount(int id);

//    void findCart(T user);
}
