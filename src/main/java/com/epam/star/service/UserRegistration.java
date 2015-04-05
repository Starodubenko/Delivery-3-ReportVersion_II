package com.epam.star.service;


import com.epam.star.dao.ClientDao;
import com.epam.star.dao.DiscountDao;
import com.epam.star.dao.PositionDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserRegistration {

    @Inject
    private ClientDao clientDao;

    @Inject
    private PositionDao positionDao;

    @Inject
    private DiscountDao discountDao;

    public void doRegistration(){

    }
}
