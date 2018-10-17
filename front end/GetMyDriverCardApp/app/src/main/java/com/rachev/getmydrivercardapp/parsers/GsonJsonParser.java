package com.rachev.getmydrivercardapp.parsers;

import com.google.gson.Gson;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;

import java.util.Arrays;
import java.util.List;

public class GsonJsonParser<T> implements JsonParser<T>
{
    private final Class<T> mClazz;
    private final Class<T[]> mArrayClazz;
    private final Gson mGson;
    
    public GsonJsonParser(Class<T> clazz, Class<T[]> arrayClazz)
    {
        mClazz = clazz;
        mArrayClazz = arrayClazz;
        mGson = new Gson();
    }
    
    @Override
    public List<T> fromJsonArray(String jsonString)
    {
        return Arrays.asList(mGson.fromJson(jsonString, mArrayClazz));
    }
    
    @Override
    public T fromJson(String jsonString)
    {
        return mGson.fromJson(jsonString, mClazz);
    }
    
    @Override
    public String toJson(T object)
    {
        return mGson.toJson(object);
    }
}
