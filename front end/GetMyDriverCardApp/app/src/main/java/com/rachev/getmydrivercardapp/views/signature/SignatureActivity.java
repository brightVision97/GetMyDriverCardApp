package com.rachev.getmydrivercardapp.views.signature;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kyanogen.signatureview.SignatureView;
import com.rachev.getmydrivercardapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SignatureActivity extends AppCompatActivity
{
    @BindView(R.id.signature_view)
    SignatureView mSignatureView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        
        ButterKnife.bind(this);
        
        getStoragePermission();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_signature, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_clear:
                mSignatureView.clearCanvas();
                showToast("Canvas cleared");
                return true;
            case R.id.action_download:
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                        System.currentTimeMillis() + ".png");
                
                Bitmap bitmap = mSignatureView.getSignatureBitmap();
                try (FileOutputStream out = new FileOutputStream(file))
                {
                    if (bitmap != null)
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    else
                        throw new FileNotFoundException();
                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally
                {
                    if (bitmap != null)
                        showToast("Image saved successfully at " + file.getPath());
                }
        }
        return true;
    }
    
    private void showToast(String message)
    {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }
    
    private void getStoragePermission()
    {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        showToast("Permission granted");
                    }
                    
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        showToast("Permission denied");
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
