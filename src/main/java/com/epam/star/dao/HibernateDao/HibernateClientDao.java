package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.ClientDao;
import com.epam.star.dao.H2dao.AbstractH2Dao;
import com.epam.star.dao.H2dao.DaoException;
import com.epam.star.dao.H2dao.DaoManager;
import com.epam.star.dao.H2dao.H2DiscountDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PositionDao;
import com.epam.star.dao.util.PaginatedList;
import com.epam.star.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MappedDao("Client")
public class HibernateClientDao extends AbstractHibernateDao<Client> implements ClientDao {

    @Override
    protected Class getEntityClass() {
        return Client.class;
    }

    @Override
    public Client findByCredentials(String login, String password) {
        
        return null;
    }

    @Override
    public boolean alreadyExist(String login) {
        return false;
    }
}
