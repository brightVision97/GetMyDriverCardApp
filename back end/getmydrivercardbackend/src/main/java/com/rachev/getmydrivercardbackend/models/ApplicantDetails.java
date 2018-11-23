package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "applicants_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicantDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "details_id")
    private int id;
    
    @NotNull
    @Column(name = "egn")
    private String egn;
    
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    
    @NotNull
    @Column(name = "middle_name")
    private String middleName;
    
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    
    @NotNull
    @Column(name = "birthdate")
    private String birthDate;
    
    @NotNull
    @Column(name = "email")
    private String email;
    
    @NotNull
    @Column(name = "address")
    private String address;
    
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;
}
