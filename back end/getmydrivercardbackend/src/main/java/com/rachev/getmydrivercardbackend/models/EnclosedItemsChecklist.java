package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnclosedItemsChecklist
{
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;
    
    @NotNull
    @Column(name = "fee")
    private double fee;
    
    @NotNull
    @Column(name = "postal_order_number")
    private String postalOrderNumber;
    
    @NotNull
    @Column(name = "delivery_number")
    private String deliveryNumber;
    
    @NotNull
    @Column(name = "last_card_enclosed", columnDefinition = "TINYINT", length = 1)
    private boolean lastCard;
    
    @NotNull
    @Column(name = "gb_driving_license_enclosed", columnDefinition = "TINYINT", length = 1)
    private boolean GBDrivingLicense;
    
    @NotNull
    @Column(name = "eu_or_eea_driving_license_enclosed", columnDefinition = "TINYINT", length = 1)
    private boolean EUorEEADrivingLicense;
    
    @NotNull
    @Column(name = "evidence_for_uk_inhabitance", columnDefinition = "TINYINT", length = 1)
    private boolean evidenceForUKInhabitance;
}
