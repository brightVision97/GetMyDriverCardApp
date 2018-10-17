package com.rachev.getmydrivercardbackend;

import com.rachev.getmydrivercardbackend.services.base.GetMyDriverCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GetmydrivercardbackendApplication
{
    private final GetMyDriverCardService getMyDriverCardService;
    
    @Autowired
    public GetmydrivercardbackendApplication(GetMyDriverCardService getMyDriverCardService)
    {
        this.getMyDriverCardService = getMyDriverCardService;
    }
    
    public static void main(String[] args)
    {
        SpringApplication.run(GetmydrivercardbackendApplication.class, args);
    }
}
