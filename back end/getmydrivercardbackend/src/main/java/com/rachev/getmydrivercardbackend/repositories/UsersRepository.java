package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserDTO, Integer> {
}
