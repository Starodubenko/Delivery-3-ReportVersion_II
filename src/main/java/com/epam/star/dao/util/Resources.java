package com.epam.star.dao.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext(unitName = "primary")
    private EntityManager em;
}
