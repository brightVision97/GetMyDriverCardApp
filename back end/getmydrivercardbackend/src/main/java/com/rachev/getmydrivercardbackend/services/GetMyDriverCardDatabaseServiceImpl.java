package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.UserDTO;
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
    public List<UserDTO> getAllUsers() {
        return usersRepository.getAll();
    }

    @Override
    public UserDTO getByEmail(String email) {
        return usersRepository.getByEmail(email);
    }


    @Override
    public void createUser(UserDTO userDTO) {
        usersRepository.create(userDTO);

    }
}
