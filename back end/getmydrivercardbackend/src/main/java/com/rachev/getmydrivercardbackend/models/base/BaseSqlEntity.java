package com.rachev.getmydrivercardbackend.models.base;

import lombok.*;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "entities")
@RequiredArgsConstructor
@Getter
@Setter
public class BaseSqlEntity
{
    @Id
    @NotNull
    @Column(name = "id")
    private final UUID id;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_timestamp")
    private LocalDateTime lastEditTimestamp;
    
   protected BaseSqlEntity()
   {
       id = null;
   }
}
