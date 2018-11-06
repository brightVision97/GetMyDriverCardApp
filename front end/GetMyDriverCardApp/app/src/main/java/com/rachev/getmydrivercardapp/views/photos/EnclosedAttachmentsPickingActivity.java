package com.rachev.getmydrivercardapp.views.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.ImagePicker;
import com.rachev.getmydrivercardapp.views.photos.base.BaseImagePickingActivity;

import java.io.File;

public class EnclosedAttachmentsPickingActivity extends BaseImagePickingActivity
        implements View.OnClickListener
{
    @BindView(R.id.take_id_pic_btn)
    Button mIdPicButton;
    
    @BindView(R.id.take_driving_license_pic_btn)
    Button mDrivingLicenseButton;
    
    @BindView(R.id.take_payment_doc_pic_btn)
    Button mPaymentDocumentButton;
    
    @BindView(R.id.id_pic_descr)
    TextView mIdPicText;
    
    @BindView(R.id.driving_license_pic_descr)
    TextView mDrivingLicenseText;
    
    @BindView(R.id.payment_doc_pic_descr)
    TextView mPaymentDocumentText;
    
    private File mIdImage;
    private File mDrivingLicenseImage;
    private File mPaymentDocumentImage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclosed_attachments__picking);
        
        ButterKnife.bind(this);
        
        mIdPicButton.setOnClickListener(this);
        mDrivingLicenseButton.setOnClickListener(this);
        mPaymentDocumentButton.setOnClickListener(this);
        
        getStoragePermission();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.take_id_pic_btn:
                startImagePickingIntent(
                        Constants.Integers.PICK_ID_IMAGE,
                        Constants.Strings.ID_CARD_FNAME);
                break;
            case R.id.take_driving_license_pic_btn:
                startImagePickingIntent(
                        Constants.Integers.PICK_DRIVING_LIC_IMAGE,
                        Constants.Strings.DRIVING_LIC_FNAME);
                break;
            case R.id.take_payment_doc_pic_btn:
                startImagePickingIntent(
                        Constants.Integers.PICK_PAYMENT_DOC_IMAGE,
                        Constants.Strings.PAYMENT_DOC_FNAME);
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
            case Constants.Integers.PICK_PAYMENT_DOC_IMAGE:
                mPaymentDocumentImage = ImagePicker.getImageFileToUpload(
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
                : R.string.id_card_filename + ": " + mIdImage.getName();
        mIdPicText.setText(firstImageDescription);
        String secondImageDescription = mDrivingLicenseImage == null
                ? Constants.Strings.NO_IMG_SELECTED
                : R.string.driving_license_filename + ": " + mDrivingLicenseImage.getName();
        mDrivingLicenseText.setText(secondImageDescription);
        String thirdImageDescription = mPaymentDocumentImage == null
                ? Constants.Strings.NO_IMG_SELECTED
                : R.string.payment_document_filename + ": " + mPaymentDocumentImage.getName();
        mPaymentDocumentText.setText(thirdImageDescription);
    }
}
