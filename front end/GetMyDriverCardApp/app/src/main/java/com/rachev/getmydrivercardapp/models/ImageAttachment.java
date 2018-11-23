package com.rachev.getmydrivercardapp.models;


import java.io.Serializable;

public class ImageAttachment implements Serializable
{
    private int id;
    private String selfieImage;
    private String idCardImage;
    private String signatureScreenshot;
    private String driverLicenseImage;
    private String prevDriverCardImage;
    
    public ImageAttachment()
    {
    }
    
    public ImageAttachment(String selfieImage, String idCardImage,
                           String signatureScreenshot, String driverLicImage)
    {
        setSelfieImage(selfieImage);
        setIdCardImage(idCardImage);
        setSignatureScreenshot(signatureScreenshot);
        setDriverLicenseImage(driverLicImage);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
       this.id = id;
    }
    
    public String getSelfieImage()
    {
        return selfieImage;
    }
    
    public void setSelfieImage(String selfieImage)
    {
        this.selfieImage = selfieImage;
    }
    
    public String getIdCardImage()
    {
        return idCardImage;
    }
    
    public void setIdCardImage(String idCardImage)
    {
        this.idCardImage = idCardImage;
    }
    
    public String getDriverLicenseImage()
    {
        return driverLicenseImage;
    }
    
    public void setDriverLicenseImage(String driverLicenseImage)
    {
        this.driverLicenseImage = driverLicenseImage;
    }
    
    public String getSignatureScreenshot()
    {
        return signatureScreenshot;
    }
    
    public void setSignatureScreenshot(String signatureScreenshot)
    {
        this.signatureScreenshot = signatureScreenshot;
    }
    
    public String getPrevDriverCardImage()
    {
        return prevDriverCardImage;
    }
    
    public void setPrevDriverCardImage(String prevDriverCardImage)
    {
        this.prevDriverCardImage = prevDriverCardImage;
    }
}
