package com.rachev.getmydrivercardbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.rachev.getmydrivercardbackend.repositories")
public class GetmydrivercardbackendApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(GetmydrivercardbackendApplication.class, args);
    }
}

