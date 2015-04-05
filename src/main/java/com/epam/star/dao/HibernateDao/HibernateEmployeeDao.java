package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.EmployeeDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Client;
import com.epam.star.entity.Employee;

import javax.persistence.Query;
import java.util.List;

@MappedDao("Employee")
public class HibernateEmployeeDao extends AbstractHibernateDao<Employee> implements EmployeeDao {

    @Override
    protected Class getEntityClass() {
        return Employee.class;
    }

    @Override
    public Employee findByCredentials(String login, String password) {
        Query query = em.createQuery("select e from Employee e where lower(e.login) = lower(" + login + ") and e.password = " + password);
        return (Employee) query.getSingleResult();
    }
}
