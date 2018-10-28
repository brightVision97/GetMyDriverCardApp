package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "applicant_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicantDetails
{
    @Id
    @NotNull
    @Column(name = "id", unique = true)
    private int id;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    private LocalDate birthDate;
    
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
    @Column(name = "email")
    private String email;
    
    // Relations missing
    @NotNull
    private Address address;
}
