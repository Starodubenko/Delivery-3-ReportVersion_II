package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.ClientDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Client;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Query;
import java.util.List;

@MappedDao("Client")
public class HibernateClientDao extends AbstractHibernateDao<Client> implements ClientDao {

    public HibernateClientDao() {
    }

    @Override
    protected Class getEntityClass() {
        return Client.class;
    }

    @Override
    public Client findByCredentials(String login, String password) {
        Query query = em.createQuery("select c from Client c where lower(c.login) = lower(" + login + ") and c.password = " + password);
        return (Client) query.getSingleResult();
    }

    @Override
    public boolean alreadyExist(String login) {
        Query query = em.createQuery("select c from Client c where lower(c.login) = lower("+login+")");
        List<Client> resultList = query.getResultList();
        return  resultList.size() > 0;
    }
}
