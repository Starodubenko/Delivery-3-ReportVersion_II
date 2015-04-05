package com.epam.star.service;


import com.epam.star.dao.ClientDao;
import com.epam.star.entity.Client;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class UserLogin {

    @Inject
    private EntityManager em;

    @Inject
    private ClientDao clientDao;

    public void doLogin(String name, String password){
        Client client = clientDao.findByCredentials(name, password);
    }
}
