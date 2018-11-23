package com.rachev.getmydrivercardapp.views.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.ImagePicker;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.photos.base.BaseImagePickingActivity;
import com.rachev.getmydrivercardapp.views.signature.SignatureActivity;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.io.File;

public class EnclosedAttachmentsPickingActivity extends BaseImagePickingActivity
        implements View.OnClickListener
{
    private File mIdImage;
    private File mDrivingLicenseImage;
    private File mPrevTachographicCardImage;
    private int mUploadedPicsCounter;
    
    @BindView(R.id.take_id_pic_btn)
    Button mIdPicButton;
    
    @BindView(R.id.take_driving_license_pic_btn)
    Button mDrivingLicenseButton;
    
    @BindView(R.id.take_prev_card_pic_btn)
    Button mPrevTachographicCardButton;
    
    @BindView(R.id.btn_next)
    Button mNextButton;
    
    @BindView(R.id.id_pic_descr)
    TextView mIdPicText;
    
    @BindView(R.id.driving_license_pic_descr)
    TextView mDrivingLicenseText;
    
    @BindView(R.id.payment_doc_pic_descr)
    TextView mPrevTachographicCardText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclosed_attachments_picking);
        setTitle("Step 3");
        
        ButterKnife.bind(this);
        
        mIdPicButton.setOnClickListener(this);
        mDrivingLicenseButton.setOnClickListener(this);
        mPrevTachographicCardButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        
        getStoragePermission();
        
        mUploadedPicsCounter = 0;
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.take_id_pic_btn:
                if (mIdImage == null)
                    ++mUploadedPicsCounter;
                startImagePickingIntent(
                        Constants.Integers.PICK_ID_IMAGE,
                        getString(R.string.id_card_filename));
                break;
            case R.id.take_driving_license_pic_btn:
                if (mDrivingLicenseImage == null)
                    ++mUploadedPicsCounter;
                startImagePickingIntent(
                        Constants.Integers.PICK_DRIVING_LIC_IMAGE,
                        getString(R.string.driving_license_filename));
                break;
            case R.id.take_prev_card_pic_btn:
                startImagePickingIntent(
                        Constants.Integers.PICK_PREV_CARD_IMAGE,
                        getString(R.string.prev_card_filename));
                break;
            case R.id.btn_next:
                if (mUploadedPicsCounter < 2)
                {
                    Methods.showCrouton(this,
                            "At least 2 images required",
                            Style.ALERT, true);
                    return;
                }
                
                BaseRequest baseRequest = (BaseRequest) getIntent().getSerializableExtra("request");
                
                Intent intent = new Intent(this, SignatureActivity.class);
                intent.putExtra("request", baseRequest);
                intent.putExtra("path1", mIdImage.getPath());
                intent.putExtra("path2", mDrivingLicenseImage.getPath());
                if (mPrevTachographicCardImage != null)
                    intent.putExtra("path3", mPrevTachographicCardImage.getPath());
                else
                    intent.putExtra("path3", "");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    
    private void startImagePickingIntent(int code, String fileName)
    {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this, fileName);
        startActivityForResult(chooseImageIntent, code);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case Constants.Integers.PICK_ID_IMAGE:
                mIdImage = ImagePicker.getImageFileToUpload(
                        this, resultCode, data);
                updateDescription();
                break;
            case Constants.Integers.PICK_DRIVING_LIC_IMAGE:
                mDrivingLicenseImage = ImagePicker.getImageFileToUpload(
                        this, resultCode, data);
                updateDescription();
                break;
            case Constants.Integers.PICK_PREV_CARD_IMAGE:
                mPrevTachographicCardImage = ImagePicker.getImageFileToUpload(
                        this, resultCode, data);
                updateDescription();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    
    private void updateDescription()
    {
        String firstImageDescription = mIdImage == null
                ? Constants.Strings.NO_IMG_SELECTED
                : getString(R.string.id_card_filename) + ": " + mIdImage.getName();
        mIdPicText.setText(firstImageDescription);
        String secondImageDescription = mDrivingLicenseImage == null
                ? Constants.Strings.NO_IMG_SELECTED
                : getString(R.string.driving_license_filename) + ": " + mDrivingLicenseImage.getName();
        mDrivingLicenseText.setText(secondImageDescription);
        String thirdImageDescription = mPrevTachographicCardImage == null
                ? Constants.Strings.NO_IMG_SELECTED
                : getString(R.string.prev_card_filename) + ": " + mPrevTachographicCardImage.getName();
        mPrevTachographicCardText.setText(thirdImageDescription);
    }
}
