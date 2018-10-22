package com.rachev.getmydrivercardbackend;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.rachev.getmydrivercardbackend.repositories")
public class GetmydrivercardbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetmydrivercardbackendApplication.class, args);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibarnate.cfg.xml")
                .addAnnotatedClass(UserDTO.class)
                .buildSessionFactory();

    }
}

