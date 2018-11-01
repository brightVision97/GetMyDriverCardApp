package com.rachev.getmydrivercardbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
public class Role
{
    @Id
    @Column(name = "role_id")
    private int id;
    
    @Nullable
    @Column(name = "role_name")
    private String name;
}
