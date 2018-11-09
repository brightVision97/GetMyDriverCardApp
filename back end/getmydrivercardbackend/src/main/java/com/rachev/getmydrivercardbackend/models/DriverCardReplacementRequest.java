package com.rachev.getmydrivercardbackend.models;

import com.rachev.getmydrivercardbackend.models.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Column;

@Getter
@Setter
public class DriverCardReplacementRequest extends BaseRequest
{
    @Nullable
    @Column(name = "replacement_reason")
    private String replacementReason;
    
    @Override
    protected void pay(double fee, Address address)
    {
    }
}
