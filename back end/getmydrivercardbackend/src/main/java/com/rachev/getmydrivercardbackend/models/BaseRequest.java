package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseRequest extends BaseSqlEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int id;
    
    @NotNull
    @Column(name = "type")
    private String type;
    
    @NotNull
    @Column(name = "status")
    private String status;
    
    @Nullable
    @Column(name = "renewal_reason")
    private String renewalReason;
    
    @Nullable
    @Column(name = "replacement_reason")
    private String replacementReason;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_details_id", referencedColumnName = "details_id")
    private ApplicantDetails applicantDetails;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_attachment_id", referencedColumnName = "attachment_id")
    private ImageAttachment imageAttachment;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_user_id", referencedColumnName = "user_id")
    private User user;
    
    @Nullable
    @Column(name = "prev_tach_card_country")
    private String tachCardIssuingCountry;
    
    @Nullable
    @Column(name = "prev_tach_card_num")
    private String tachCardNumber;
    
    @Nullable
    @Column(name = "driving_lic_country")
    private String drivingLicIssuingCountry;
    
    @Nullable
    @Column(name = "driving_lic_num")
    private String drivingLicNumber;
    
    @Nullable
    @Column(name = "replacement_incident_date")
    private String replacementIncidentDate;
    
    @Nullable
    @Column(name = "replacement_incident_place")
    private String replacementIncidentPlace;
}
