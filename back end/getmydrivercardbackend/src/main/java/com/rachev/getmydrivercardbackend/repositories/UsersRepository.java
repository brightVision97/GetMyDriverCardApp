package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
}
