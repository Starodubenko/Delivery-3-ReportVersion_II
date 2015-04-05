package com.epam.star;


import com.epam.star.entity.Client;
import com.epam.star.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Random;

public class Main {

    private static Random rnd = new Random();


    public static void main(String[] args) throws SQLException, ParseException {

        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        Client client = new Employee();

        client.setLastName("Starodubenko");
        client.setFirstName("Rodion");
        client.setMiddleName("Victorovich");
        client.setLogin("Rody");
        client.setPassword("1");
        client.setEmail("Rody10@mail.ru");
        client.setMobilephone("87770068801");
        client.setAvatar(null);
        client.setDeleted(false);
        client.setDiscount(null);
        client.setPosition(null);

        session.getTransaction().begin();
        session.persist(client);
        session.getTransaction().commit();

    }


}
