package com.rachev.getmydrivercardapp.views.signature;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.cardrequest.preview.RequestPreviewActivity;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SignatureActivity extends AppCompatActivity
{
    @BindView(R.id.signature_view)
    SignatureView mSignatureView;
    
    private BaseRequest mBaseRequest;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        setTitle("Final step");
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        ButterKnife.bind(this);
        
        mBaseRequest = (BaseRequest) getIntent().getSerializableExtra("request");
        
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
                if (mSignatureView.isBitmapEmpty())
                {
                    Methods.showCrouton(this,
                            "You must provide your signature",
                            Style.ALERT, true);
                    return false;
                }
                
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                        System.currentTimeMillis() + Constants.Strings.PNG_SUFFIX);
                
                Bitmap bitmap = mSignatureView.getSignatureBitmap();
                
                mBaseRequest.getImageAttachment().setSignatureScreenshot(Methods.encodeBitmapToBase64String(
                        Methods.convertBitmapToFile(bitmap, this,
                                System.currentTimeMillis() + Constants.Strings.PNG_SUFFIX)
                                .getPath()));
                
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
                    {
                        new AlertDialog.Builder(this).
                                setMessage("Are you sure about your signature?")
                                .setPositiveButton("Yes", (dialog, which) ->
                                {
                                    Intent intent = new Intent(this, RequestPreviewActivity.class);
                                    intent.putExtra("request", mBaseRequest);
                                    intent.putExtra("isOriginList", false);
                                    intent.putExtra("path1", getIntent().getStringExtra("path1"));
                                    intent.putExtra("path2", getIntent().getStringExtra("path2"));
                                    intent.putExtra("path3", getIntent().getStringExtra("path3"));
                                    startActivity(intent);
                                })
                                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                .setCancelable(true)
                                .create()
                                .show();
                    }
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
