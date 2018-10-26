package com.rachev.getmydrivercardbackend;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GetmydrivercardbackendApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GetmydrivercardbackendApplication.class, args);
    }
    
    @Bean
    public SessionFactory sessionFactory()
    {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(UserDTO.class)
                .buildSessionFactory();
    }
}

