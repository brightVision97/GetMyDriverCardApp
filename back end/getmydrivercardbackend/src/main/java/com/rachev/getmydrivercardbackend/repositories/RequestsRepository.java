package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.BaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RequestsRepository extends JpaRepository<BaseRequest, Integer>
{
    List<BaseRequest> getAllByUserId(int userId);
    
    @Transactional
    @Modifying
    @Query("update BaseRequest r set r.status =:status where r.id =:id")
    void setRequestStatusById(@Param("id") int requestId, @Param("status") String requestStatus);
}
