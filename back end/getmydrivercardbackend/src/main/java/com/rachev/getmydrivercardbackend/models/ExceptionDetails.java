package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails
{
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
