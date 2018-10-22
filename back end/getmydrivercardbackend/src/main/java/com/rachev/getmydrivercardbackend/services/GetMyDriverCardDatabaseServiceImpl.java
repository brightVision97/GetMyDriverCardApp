package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.User;
import com.rachev.getmydrivercardbackend.repositories.UsersRepository;
import com.rachev.getmydrivercardbackend.services.base.GetMyDriverCardService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import java.util.List;

@Service
public class GetMyDriverCardDatabaseServiceImpl implements GetMyDriverCardService {

    private final UsersRepository usersRepository;

    @Autowired
    public GetMyDriverCardDatabaseServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.getAll();
    }

    @Override
    public void createUser(User user) {
     usersRepository.create(user);
    }
}
