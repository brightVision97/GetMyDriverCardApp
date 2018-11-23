package com.rachev.getmydrivercardapp.repositories.base;

import java.io.IOException;
import java.util.List;

public interface BaseRepository<T>
{
    List<T> getAll() throws Exception;
    
    T getById(int id) throws IOException;
    
    T add(T item) throws Exception;
}
