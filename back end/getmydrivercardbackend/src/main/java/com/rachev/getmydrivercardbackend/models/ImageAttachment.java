package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attachments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageAttachment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private int Id;
    
    @NotNull
    @Column(name = "selfie_pic")
    private String selfieImage;
    
    @Nullable
    @Column(name = "user_id_card_pic")
    private String idCardImage;
    
    @NotNull
    @Column(name = "signature_screenshot")
    private String signatureScreenshot;
    
    @Nullable
    @Column(name = "driver_lic_pic")
    private String driverLicenseImage;
    
    @Nullable
    @Column(name = "prev_driver_card_pic")
    private String prevDriverCardImage;
    
    @Nullable
    @Column(name = "prev_foreign_card_pic")
    private String prevForeignDriverCardImage;
}
