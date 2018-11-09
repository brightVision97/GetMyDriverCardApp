package com.rachev.getmydrivercardbackend.models.base;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@Table(name = "entities")
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseSqlEntity
{
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    private Date creationTimestamp;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_timestamp")
    private Date lastEditTimestamp;
    
    @Version
    private int version;
}
