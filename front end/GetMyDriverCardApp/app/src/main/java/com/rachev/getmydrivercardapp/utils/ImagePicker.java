package com.rachev.getmydrivercardapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import id.zelory.compressor.Compressor;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("JavaReflectionMemberAccess")
public class ImagePicker
{
    private static String lastTempImg = "";
    private static boolean strictModeBypassed = false;
    
    public static Intent getPickImageIntent(Context context, String imagePath)
    {
        lastTempImg = imagePath;
        deleteExistingFile(context, lastTempImg);
        
        return constructImagePickerIntent(context);
    }
    
    public static Intent getPickImageIntent(Context context)
    {
        generateNextImgFile(context);
        
        return constructImagePickerIntent(context);
    }
    
    private static Intent constructImagePickerIntent(Context context)
    {
        if (!strictModeBypassed)
            bypassStrictMode();
        
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();
        
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(prepareNextImgFile(context)));
        
        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);
        
        if (intentList.size() > 0)
        {
            chooserIntent = Intent.createChooser(intentList.remove(
                    intentList.size() - 1), Constants.Strings.PICK_IMG);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toArray(new Parcelable[]{}));
        }
        
        return chooserIntent;
    }
    
    private static void bypassStrictMode()
    {
        try
        {
            Method m = StrictMode.class.getMethod(Constants.Strings.BYPASS_METHOD_NAME);
            m.invoke(null);
            strictModeBypassed = true;
        } catch (Exception ignored)
        {
        }
    }
    
    private static File prepareNextImgFile(Context context)
    {
        File imageFile = new File(context.getExternalFilesDir(null), lastTempImg);
        imageFile.setWritable(true);
        
        return imageFile;
    }
    
    private static void generateNextImgFile(Context context)
    {
        if (!lastTempImg.isEmpty() && lastTempImg.contains(Constants.Strings.TEMP_IMG_PREFIX))
            deleteExistingFile(context, lastTempImg);
        
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat(Constants.Strings.DATE_PATTERN).format(new Date());
        lastTempImg = Constants.Strings.TEMP_IMG_PREFIX + timeStamp + Constants.Strings.TEMP_IMG_SUFFIX;
    }
    
    private static void deleteExistingFile(Context context, String fileName)
    {
        File file = new File(context.getExternalFilesDir(null), fileName);
        
        if (file.exists())
            file.delete();
    }
    
    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent)
    {
        List<ResolveInfo> resInfo = context
                .getPackageManager()
                .queryIntentActivities(intent, 0);
        
        for (ResolveInfo resolveInfo : resInfo)
        {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        
        return list;
    }
    
    public static Bitmap getImageFromResult(Context context, int resultCode, Intent imageReturnedIntent)
    {
        Bitmap bm = null;
        if (resultCode == Activity.RESULT_OK)
        {
            boolean isCamera = isFromCamera(imageReturnedIntent);
            Uri selectedImage = isCamera
                    ? Uri.fromFile(getTempFile(context))
                    : imageReturnedIntent.getData();
            
            bm = getImageResized(context, selectedImage);
            int rotation = getRotation(context, selectedImage, isCamera);
            bm = rotate(bm, rotation);
        }
        
        return bm;
    }
    
    public static File getImageFileToUpload(Context context, int resultCode, Intent imageReturnedIntent)
    {
        File actualImage = null;
        File compressedImage = null;
        
        if (resultCode == Activity.RESULT_OK)
        {
            if (isFromCamera(imageReturnedIntent))
                actualImage = getTempFile(context);
            
            actualImage = new File(getAbsolutePathFromUri(
                    imageReturnedIntent.getData(), context));
        }
        
        return compressImageFile(context, actualImage);
    }
    
    private static File compressImageFile(Context context, File actualFile)
    {
        File compressedImage = null;
        try
        {
            compressedImage = new Compressor(context).compressToFile(actualFile);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return compressedImage;
    }
    
    private static boolean isFromCamera(Intent imageReturnedIntent)
    {
        return !lastTempImg.isEmpty()
                && (imageReturnedIntent == null
                || imageReturnedIntent.getData() == null
                || imageReturnedIntent.getData().toString().contains(lastTempImg));
    }
    
    private static File getTempFile(Context context)
    {
        return new File(context.getExternalFilesDir(null), lastTempImg);
    }
    
    private static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        
        AssetFileDescriptor fileDescriptor = null;
        try
        {
            fileDescriptor = context.getContentResolver()
                    .openAssetFileDescriptor(theUri, "r");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        return BitmapFactory.decodeFileDescriptor(
                fileDescriptor.getFileDescriptor(),
                null, options);
    }
    
    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     **/
    private static Bitmap getImageResized(Context context, Uri selectedImage)
    {
        Bitmap bm;
        int[] sampleSizes = {5, 3, 2, 1};
        int i = 0;
        do
        {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i]);
            i++;
        } while (bm.getWidth() < Constants.Integers.DEFAULT_MIN_WIDTH_QUALITY && i < sampleSizes.length);
        
        return bm;
    }
    
    private static int getRotation(Context context, Uri imageUri, boolean isCamera)
    {
        return isCamera
                ? getRotationFromCamera(context, imageUri)
                : getRotationFromGallery(context, imageUri);
    }
    
    private static int getRotationFromCamera(Context context, Uri imageFile)
    {
        int rotate = 0;
        try
        {
            context.getContentResolver().notifyChange(imageFile, null);
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return rotate;
    }
    
    private static int getRotationFromGallery(Context context, Uri imageUri)
    {
        int result = 0;
        String[] columns = {MediaStore.Images.Media.ORIENTATION};
        try (Cursor cursor = context.getContentResolver()
                .query(imageUri, columns, null,
                        null, null))
        {
            if (cursor != null && cursor.moveToFirst())
            {
                int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
                result = cursor.getInt(orientationColumnIndex);
            }
        } catch (Exception ignored)
        {
        }
        
        return result;
    }
    
    private static Bitmap rotate(Bitmap bm, int rotation)
    {
        if (rotation != 0)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            
            return Bitmap.createBitmap(bm, 0, 0,
                    bm.getWidth(), bm.getHeight(),
                    matrix, true);
        }
        
        return bm;
    }
    
    private static String getAbsolutePathFromUri(Uri uri, Context context)
    {
        String result = "";
        String documentID;
        String pathSegments[] = uri.getLastPathSegment().split(":");
        documentID = pathSegments[pathSegments.length - 1];
        String mediaPath = MediaStore.Images.Media.DATA;
        Cursor imageCursor = context.getContentResolver()
                .query(uri, new String[]{mediaPath},
                        MediaStore.Images.Media._ID + "=" + documentID,
                        null, null);
        if (imageCursor != null)
        {
            if (imageCursor.moveToFirst())
                result = imageCursor.getString(imageCursor.getColumnIndex(mediaPath));
            imageCursor.close();
        }
        
        return result;
    }
}
