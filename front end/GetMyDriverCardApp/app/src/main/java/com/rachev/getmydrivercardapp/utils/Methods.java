package com.rachev.getmydrivercardapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.rachev.getmydrivercardapp.models.User;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    public static byte[] convertBitmapToByteArr(Bitmap bitmap)
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
    
    public static Bitmap convertByteArrToBitmap(byte[] bytes)
    {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    
    public static File convertBitmapToFile(Bitmap bitmap, Context context, String filename)
    {
        File file = new File(context.getCacheDir(), filename);
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        
        bitmap.compress(Bitmap.CompressFormat.PNG, Constants.Integers.BITMAP_COMPRESS_QUALITY, byteOutputStream);
        byte[] bitmapData = byteOutputStream.toByteArray();
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            fileOutputStream.write(bitmapData);
            fileOutputStream.flush();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return file;
    }
    
    public static Bitmap convertFileToBitmap(File file)
    {
        return BitmapFactory.decodeFile(file.getPath());
    }
    
    public static String encodeBitmapToBase64String(String filePath)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
        {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            bytes = outputStream.toByteArray();
            outputStream.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    
    public static Bitmap decodeBitmapFromBase64String(String encodedImage)
    {
        byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
    
    public static User getUserFromSmartUser(SmartUser smartUser)
    {
        User userToSend = new User();
        
        if (smartUser instanceof SmartGoogleUser)
        {
            String customUsername = smartUser.getEmail()
                    .substring(0, smartUser.getEmail().indexOf('@'))
                    .toLowerCase().replace(" ", "")
                    + smartUser.getUserId().substring(0, 4);
            userToSend.setUsername(customUsername);
            userToSend.setGoogleId(smartUser.getUserId());
        } else if (smartUser instanceof SmartFacebookUser)
        {
            String customUsername =
                    (((SmartFacebookUser) smartUser).getProfileName()
                            .toLowerCase().replace(" ", ".")
                            + smartUser.getUserId().substring(0, 4));
            userToSend.setUsername(customUsername);
            userToSend.setFacebookId(smartUser.getUserId());
        }
        
        return userToSend;
    }
    
    public static boolean isEgnValid(String egn)
    {
        final int[] EGN_WEIGHTS = {2, 4, 8, 5, 10, 9, 7, 3, 6};
        
        if (egn.length() != 10)
            return false;
        
        int year = Integer.parseInt(egn.substring(0, 2));
        int month = Integer.parseInt(egn.substring(2, 4));
        int day = Integer.parseInt(egn.substring(4, 6));
        if (month > 40)
        {
            if (!isDateValid(day, month - 40, year + 2000))
                return false;
        } else
        {
            if (month > 20)
            {
                if (!isDateValid(day, month - 20, year + 1800))
                    return false;
            } else
            {
                if (!isDateValid(day, month, year + 1900))
                    return false;
            }
        }
        
        int checksum = egn.charAt(9) - '0';
        int egnsum = 0;
        
        for (int i = 0; i < 9; i++)
            egnsum += (egn.charAt(i) - '0') * EGN_WEIGHTS[i];
        
        int validChecksum = egnsum % 11;
        if (validChecksum == 10)
            validChecksum = 0;
        
        return checksum == validChecksum;
    }
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isDateValid(int day, int month, int year)
    {
        LocalDate localDate = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String parsedDate = localDate.format(formatter);
        
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            dateFormat.setLenient(false);
            dateFormat.parse(parsedDate);
            
            return true;
        } catch (ParseException e)
        {
            return false;
        }
    }
}
