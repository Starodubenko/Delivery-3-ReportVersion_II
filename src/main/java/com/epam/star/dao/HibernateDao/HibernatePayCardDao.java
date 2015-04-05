package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.MappedDao;
import com.epam.star.dao.PayCardDao;
import com.epam.star.entity.Order;
import com.epam.star.entity.PayCard;

import javax.persistence.Query;
import java.util.List;

@MappedDao("PayCard")
public class HibernatePayCardDao extends AbstractHibernateDao<PayCard> implements PayCardDao {

    @Override
    protected Class getEntityClass() {
        return null;
    }

    @Override
    public PayCard findBySerialNumber(String serNum) {
        Query query = em.createQuery("select p from PayCard p where p.serialNumber = " + serNum);
        return (PayCard) query.getSingleResult();
    }

    @Override
    public PayCard findBySecretNumber(String secNum) {
        Query query = em.createQuery("select p from PayCard p where p.secretNumber = " + secNum);
        return (PayCard) query.getSingleResult();
    }

    @Override
    public PayCard findByStatus(String status) {
        Query query = em.createQuery("select p from PayCard p where p.statusPayCard = " + status);
        return (PayCard) query.getSingleResult();
    }
}
