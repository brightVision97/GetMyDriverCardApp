package com.rachev.getmydrivercardapp.views.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.ImagePicker;
import com.rachev.getmydrivercardapp.views.photos.base.BaseImagePickingActivity;

public class SelfiePickingActivity extends BaseImagePickingActivity
{
    @BindView(R.id.pick_bitmap)
    Button pickImageBitmap;
    
    @BindView(R.id.choosen_pic)
    ImageView bitmapView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_picking);
        
        ButterKnife.bind(this);
        
        pickImageBitmap.setOnClickListener(
                v -> proceedToImagePicking(Constants.Integers.PICK_IMAGE_BITMAP_ID));
        
        getStoragePermission();
    }
    
    private void proceedToImagePicking(int code)
    {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, code);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case Constants.Integers.PICK_IMAGE_BITMAP_ID:
                bitmapView.setVisibility(View.VISIBLE);
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                bitmapView.setImageBitmap(bitmap);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
