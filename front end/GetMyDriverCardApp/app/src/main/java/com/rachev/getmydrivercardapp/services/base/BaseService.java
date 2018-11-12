package com.rachev.getmydrivercardapp.services.base;

import java.util.List;

public interface BaseService<T>
{
    List<T> getAll() throws Exception;
    
    T create(T obj) throws Exception;
}
