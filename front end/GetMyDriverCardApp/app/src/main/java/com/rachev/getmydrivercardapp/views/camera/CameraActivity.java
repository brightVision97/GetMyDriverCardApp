package com.rachev.getmydrivercardapp.views.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.rachev.getmydrivercardapp.R;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.camera)
    CameraView mCameraView;
    
    @BindView(R.id.capture_photo)
    ImageButton mCaptureButton;
    
    @BindView(R.id.toggle_camera)
    ImageButton mToggleButton;
    
    @BindView(R.id.camera_root)
    ViewGroup controlPanel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_camera);
        
        ButterKnife.bind(this);
        
        mCaptureButton.setOnClickListener(this);
        mToggleButton.setOnClickListener(this);
        
        mCameraView.setLifecycleOwner(this);
        mCameraView.addCameraListener(new CameraListener()
        {
            public void onPictureTaken(byte[] jpeg)
            {
                onPicture(jpeg, mCameraView.getFacing() == Facing.FRONT);
            }
        });
    }
    
    private void onPicture(byte[] jpeg, boolean isFrontFacing)
    {
        PicturePreviewActivity.setImage(jpeg, isFrontFacing);
        
        Intent intent = new Intent(this, PicturePreviewActivity.class);
        startActivity(intent);
    }
    
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.capture_photo:
                mCameraView.capturePicture();
                break;
            case R.id.toggle_camera:
                mCameraView.toggleFacing();
                break;
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        boolean valid = true;
        
        for (int grantResult : grantResults)
            valid = valid && grantResult == PackageManager.PERMISSION_GRANTED;
        
        if (valid && !mCameraView.isStarted())
            mCameraView.start();
    }
}
