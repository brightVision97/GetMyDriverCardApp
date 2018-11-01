package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>
{
}
