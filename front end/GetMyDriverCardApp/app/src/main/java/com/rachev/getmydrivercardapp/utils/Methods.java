package com.rachev.getmydrivercardapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class Methods
{
    public static void showCrouton(Activity activity, String message, Style style, boolean important)
    {
        Crouton.makeText(activity, message,
                new Style.Builder(style)
                        .setHeight(Constants.Integers.CROUTON_HEIGHT)
                        .setTextSize(Constants.Integers.CROUTON_TEXT_SIZE)
                        .build())
                .setConfiguration(new Configuration.Builder()
                        .setDuration(important
                                ? Constants.Integers.CROUTON_LONG_DURATION
                                : Constants.Integers.CROUTON_SHORT_DURATION)
                        .build())
                .show();
    }
    
    public static void showToast(Context context, String message, boolean important)
    {
        Toast.makeText(context, message,
                important ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
                .show();
    }
    
    public static void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    public static void hideKeyboardFrom(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    public byte[] convertBitmapToByteArr(Bitmap bitmap)
    {
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(buffer);
        
        byte[] bytes = new byte[size];
        
        try
        {
            buffer.get(bytes, 0, bytes.length);
        } catch (BufferUnderflowException e)
        {
            e.printStackTrace();
        }
        
        return bytes;
    }
    
    public Bitmap convertByteArrToBitmap(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
