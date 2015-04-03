package com.epam.star.dao.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

    @Produces
    @PersistenceContext(unitName = "primary")
    @SuppressWarnings("unused")
    private EntityManager em;
}
