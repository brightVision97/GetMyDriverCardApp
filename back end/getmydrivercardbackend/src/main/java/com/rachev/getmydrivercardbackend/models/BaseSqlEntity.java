package com.rachev.getmydrivercardbackend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseSqlEntity
{
    @NotNull
    @Column(name = "record_creation_date")
    private String recordCreationDate;
    
    @NotNull
    @Column(name = "record_last_edit_date")
    private String recordLastEditedDate;
    
    public BaseSqlEntity()
    {
        recordCreationDate = LocalDate.now().toString();
        recordLastEditedDate = LocalDate.now().toString();
    }
}
