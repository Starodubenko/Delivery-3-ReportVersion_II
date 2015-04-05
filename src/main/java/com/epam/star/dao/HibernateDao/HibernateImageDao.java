package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.H2dao.DaoException;
import com.epam.star.dao.ImageDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Image;

import javax.persistence.Query;
import java.util.List;

@MappedDao("Image")
public class HibernateImageDao extends AbstractHibernateDao<Image> implements ImageDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public Image findByFilename(String filename) throws DaoException {
        Query query = em.createQuery("select i from Image i where lower(i.filename) = lower(" + filename + ")");
        return (Image) query.getSingleResult();
    }
}
