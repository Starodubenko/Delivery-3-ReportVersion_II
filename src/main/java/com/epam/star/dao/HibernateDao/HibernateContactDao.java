package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.ContactDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Contact;

@MappedDao("Contact")
public class HibernateContactDao extends AbstractHibernateDao<Contact> implements ContactDao {

    @Override
    protected Class getEntityClass() {
        return Contact.class;
    }
}
