package com.epam.star.dao;

import com.epam.star.entity.Client;

import java.sql.SQLException;

public interface ClientDao extends Dao<Client> {

    public Client findByCredentials(String login, String password);

    public boolean alreadyExist(String login);
}
