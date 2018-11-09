package com.rachev.getmydrivercardbackend.models.base;

import com.rachev.getmydrivercardbackend.models.ApplicantDetails;
import com.rachev.getmydrivercardbackend.models.Address;
import com.rachev.getmydrivercardbackend.models.Image;
import com.rachev.getmydrivercardbackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "card_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseRequest extends BaseSqlEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int id;
    
    @NotNull
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private int userId;
    
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "details_id")
    private ApplicantDetails applicantDetails;
    
    @NotNull
    @Column(name = "type")
    private String type;
    
    @NotNull
    @Column(name = "status")
    private String status;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "requests_attachments",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id"))
    List<Image> attachments;
    
    protected abstract void pay(double fee, Address address);
}
