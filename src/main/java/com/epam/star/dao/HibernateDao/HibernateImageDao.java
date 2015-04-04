package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.H2dao.DaoException;
import com.epam.star.dao.ImageDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Image;

@MappedDao("Image")
public class HibernateImageDao extends AbstractHibernateDao<Image> implements ImageDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public Image findByFilename(String filename) throws DaoException {
        return null;
    }
}
