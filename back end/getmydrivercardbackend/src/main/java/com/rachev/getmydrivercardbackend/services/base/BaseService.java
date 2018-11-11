package com.rachev.getmydrivercardbackend.services.base;

import java.util.List;

public interface BaseService<T>
{
    List<T> getAll();
    
    T getById(int id);
    
    T add(T object);
}
