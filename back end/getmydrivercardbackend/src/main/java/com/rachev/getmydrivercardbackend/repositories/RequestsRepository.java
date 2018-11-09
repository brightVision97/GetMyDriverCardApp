package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.base.BaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsRepository extends JpaRepository<BaseRequest, Integer>
{
}
