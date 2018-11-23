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
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.models.ImageAttachment;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.ImagePicker;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.photos.base.BaseImagePickingActivity;

public class SelfiePickingActivity extends BaseImagePickingActivity implements View.OnClickListener
{
    private BaseRequest mBaseRequest;
    
    @BindView(R.id.pick_bitmap)
    Button mImagePickButton;
    
    @BindView(R.id.btn_next)
    Button mNextButton;
    
    @BindView(R.id.choosen_pic)
    ImageView mBitmapView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_picking);
        setTitle("Step 2");
        
        ButterKnife.bind(this);
        
        mBaseRequest = (BaseRequest) getIntent().getSerializableExtra("request");
        
        mImagePickButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        
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
                mBitmapView.setVisibility(View.VISIBLE);
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                mBitmapView.setImageBitmap(bitmap);
                
                ImageAttachment imageAttachment = new ImageAttachment();
                imageAttachment.setSelfieImage(Methods.encodeBitmapToBase64String(
                        Methods.convertBitmapToFile(bitmap, this,
                                System.currentTimeMillis() + Constants.Strings.PNG_SUFFIX)
                                .getPath()));
                mBaseRequest.setImageAttachment(imageAttachment);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.pick_bitmap:
                proceedToImagePicking(Constants.Integers.PICK_IMAGE_BITMAP_ID);
                break;
            case R.id.btn_next:
                Intent intent = new Intent(this, EnclosedAttachmentsPickingActivity.class);
                intent.putExtra("request", mBaseRequest);
                startActivity(intent);
                break;
        }
    }
}
