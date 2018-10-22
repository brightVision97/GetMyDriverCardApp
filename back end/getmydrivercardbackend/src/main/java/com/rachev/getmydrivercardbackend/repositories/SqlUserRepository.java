package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlUserRepository implements UsersRepository {
    
    private final SessionFactory sessionFactory;
    
    @Autowired
    public SqlUserRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void create(UserDTO user) {
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
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