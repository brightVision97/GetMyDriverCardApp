package com.rachev.getmydrivercardapp.repositories.base;

import java.util.List;

public interface Repository<T>
{
    List<T> getAll() throws Exception;
    
    T add(T item) throws Exception;
}
