package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address
{
    @Id
    @NotNull
    @Column(name = "id", unique = true)
    private int id;
    
    @NotNull
    @Column(name = "city")
    private String city;
    
    @NotNull
    @Column(name = "street")
    private String street;
    
    @NotNull
    @Column(name = "postcode")
    private String postcode;
}
