package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import com.rachev.getmydrivercardbackend.repositories.UsersRepository;
import com.rachev.getmydrivercardbackend.services.base.GetMyDriverCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return usersRepository.findAll();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return usersRepository.save(userDTO);
    }
}
