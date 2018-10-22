package com.rachev.getmydrivercardbackend.repositories;


import com.rachev.getmydrivercardbackend.models.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SqlUserRepository implements UsersRepository {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void create(UserDTO user) {
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> result = new ArrayList<>();
        try (
                Session session = sessionFactory.openSession();
        ) {
            session.beginTransaction();
            result = session.createQuery("from UserDTO").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }
}