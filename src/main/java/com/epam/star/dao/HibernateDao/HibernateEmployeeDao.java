package com.epam.star.dao.HibernateDao;

import com.epam.star.dao.EmployeeDao;
import com.epam.star.dao.MappedDao;
import com.epam.star.entity.Employee;

@MappedDao("Employee")
public class HibernateEmployeeDao extends AbstractHibernateDao<Employee> implements EmployeeDao {

    @Override
    protected Class getEntityClass() {
        return Employee.class;
    }

    @Override
    public Employee findByCredentials(String login, String password) {
        return null;
    }
}
