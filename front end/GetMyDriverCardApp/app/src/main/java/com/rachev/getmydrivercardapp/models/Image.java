package com.rachev.getmydrivercardapp.models;

public class Image
{
    private int id;
    private byte[] image;
    private String attachmentPurpose;
    
    public Image()
    {
    }
    
    public Image(byte[] image, String attachmentPurpose)
    {
        setImage(image);
        setAttachmentPurpose(attachmentPurpose);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public byte[] getImage()
    {
        return image;
    }
    
    public void setImage(byte[] image)
    {
        this.image = image;
    }
    
    public String getAttachmentPurpose()
    {
        return attachmentPurpose;
    }
    
    public void setAttachmentPurpose(String attachmentPurpose)
    {
        this.attachmentPurpose = attachmentPurpose;
    }
}
