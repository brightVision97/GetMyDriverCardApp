package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.User;
import com.rachev.getmydrivercardbackend.repositories.base.UsersRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlUsersRepository implements UsersRepository
{
    private final SessionFactory sessionFactory;
    
    @Autowired
    public SqlUsersRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void create(User user)
    {
        user.setRole("user");
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public User getByUsername(String username)
    {
        User result = null;
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            result = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            transaction.commit();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return result;
    }
    
    @Override
    public List<User> getAll()
    {
        List<User> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            result = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
}