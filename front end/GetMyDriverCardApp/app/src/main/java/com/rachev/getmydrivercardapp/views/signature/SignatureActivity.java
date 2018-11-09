package com.rachev.getmydrivercardapp.views.signature;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

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
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
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
                return true;
            case R.id.action_download:
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                        System.currentTimeMillis() + Constants.Strings.PNG_SUFFIX);
                
                Bitmap bitmap = mSignatureView.getSignatureBitmap();
                try (FileOutputStream out = new FileOutputStream(file))
                {
                    if (bitmap != null)
                        bitmap.compress(
                                Bitmap.CompressFormat.PNG,
                                Constants.Integers.BITMAP_COMPRESS_QUALITY,
                                out);
                    else
                        throw new FileNotFoundException();
                } catch (Exception e)
                {
                    Methods.showToast(getApplicationContext(),
                            e.getMessage(), true);
                } finally
                {
                    if (bitmap != null)
                        Methods.showToast(getApplicationContext(),
                                Constants.Strings.SIGNATURE_SAVED_AT + file.getPath(),
                                false);
                }
        }
        return true;
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
                        Crouton.makeText(SignatureActivity.this,
                                Constants.Strings.PERMISSIONS_GRANTED,
                                Style.CONFIRM)
                                .show();
                    }
                    
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Crouton.makeText(SignatureActivity.this,
                                Constants.Strings.PERMISSIONS_DENIED,
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
