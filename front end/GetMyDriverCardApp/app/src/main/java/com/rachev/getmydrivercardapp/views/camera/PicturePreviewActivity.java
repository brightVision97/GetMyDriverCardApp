package com.rachev.getmydrivercardapp.views.camera;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.otaliastudios.cameraview.CameraUtils;
import com.rachev.getmydrivercardapp.R;

import java.lang.ref.WeakReference;

public class PicturePreviewActivity extends Activity
{
    private static boolean mIsFrontFacing;
    private static WeakReference<byte[]> mImage;
    
    @BindView(R.id.image)
    ImageView mImageView;
    
    public static void setImage(@Nullable byte[] imageBytes, boolean isFrontFacing)
    {
        mIsFrontFacing = isFrontFacing;
        
        mImage = (imageBytes != null)
                ? new WeakReference<>(imageBytes)
                : null;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        
        ButterKnife.bind(this);
        
        byte[] bitmap = (mImage == null)
                ? null
                : mImage.get();
        
        if (bitmap == null)
        {
            finish();
            return;
        }
        
        CameraUtils.decodeBitmap(bitmap, 1000, 1000, bitmapCallback ->
        {
            mImageView.setImageBitmap(bitmapCallback);
            
            if (mIsFrontFacing)
                mImageView.setRotationY(180);
        });
    }
}