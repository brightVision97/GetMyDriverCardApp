package com.rachev.getmydrivercardbackend.models.base;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "entities")
@NoArgsConstructor
@Getter
@Setter
public class BaseSqlEntity
{
    @Id
    @NotNull
    @Column(name = "id")
    private int id;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    private Date creationTimestamp;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_timestamp")
    private Date lastEditTimestamp;
}
