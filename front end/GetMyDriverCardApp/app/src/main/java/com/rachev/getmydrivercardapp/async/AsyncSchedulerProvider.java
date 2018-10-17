package com.rachev.getmydrivercardapp.async;

import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AsyncSchedulerProvider implements SchedulerProvider
{
    private static SchedulerProvider instance;
    
    private AsyncSchedulerProvider()
    {
    }
    
    public static SchedulerProvider getInstance()
    {
        if (instance == null)
            instance = new AsyncSchedulerProvider();
        
        return instance;
    }
    
    @Override
    public Scheduler background()
    {
        return Schedulers.io();
    }
    
    @Override
    public Scheduler ui()
    {
        return AndroidSchedulers.mainThread();
    }
}
