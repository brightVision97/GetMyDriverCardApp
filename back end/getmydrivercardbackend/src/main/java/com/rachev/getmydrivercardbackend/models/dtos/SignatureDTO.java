package com.rachev.getmydrivercardbackend.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// WIP
@Entity
@Table(name = "attachments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignatureDTO
{
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;
    
    @NotNull
    @Column(columnDefinition = "BLOB")
    private byte[] signatureImage;
}
