package com.rachev.getmydrivercardapp.views.camera;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.rachev.getmydrivercardapp.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

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
        
        getCameraPermission();
        
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
    
    private void getCameraPermission()
    {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        Crouton.makeText(CameraActivity.this,
                                "Permission granted",
                                Style.CONFIRM)
                                .show();
                    }
                    
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Crouton.makeText(CameraActivity.this,
                                "Permission denied",
                                Style.ALERT)
                                .show();
                    }
                    
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }
}
