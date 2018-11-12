package com.rachev.getmydrivercardapp.views.cardrequest.preview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.utils.enums.RequestStatus;
import com.rachev.getmydrivercardapp.views.home.HomeActivity;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RequestPreviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        RequestPreviewContracts.View, RequestPreviewContracts.Navigator
{
    private static BaseRequest mFinalRequest;
    private RequestPreviewContracts.Presenter mPresenter;
    
    @BindView(R.id.admin_panel)
    android.support.v7.widget.Toolbar mAdminToolbar;
    
    @BindView(R.id.admin_panel_change_status_spinner)
    Spinner mAdminPanelSpinner;
    
    @BindView(R.id.admin_panel_request_status_text)
    TextView mAdminPanelStatusText;
    
    @BindView(R.id.egn_text)
    TextView mEgnText;
    
    @BindView(R.id.first_name_text)
    TextView mFirstNameText;
    
    @BindView(R.id.middle_name_text)
    TextView mMiddleNameText;
    
    @BindView(R.id.last_name_text)
    TextView mLastNameText;
    
    @BindView(R.id.birthdate_text)
    TextView mBirthdateText;
    
    @BindView(R.id.address_text)
    TextView mAddressText;
    
    @BindView(R.id.phone_num_text)
    TextView mPhoneNumText;
    
    @BindView(R.id.email_text)
    TextView mEmailText;
    
    @BindView(R.id.applicant_selfie_img)
    ImageView mSelfieImage;
    
    @BindView(R.id.applicant_signature_img)
    ImageView mSignatureImage;
    
    @BindView(R.id.applicant_id_card_img)
    ImageView mIdCardImage;
    
    @BindView(R.id.applicant_driving_license_img)
    ImageView mDrivingLicImage;
    
    @BindView(R.id.btn_send)
    Button mSendButton;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_preview);
        setTitle("Preview");
        
        ButterKnife.bind(this);
    
        setPresenter(new RequestPreviewPresenter(this));
        if (!getIntent().getBooleanExtra("isOriginList", false))
        {
            mFinalRequest = (BaseRequest) getIntent().getSerializableExtra("request");
            mFinalRequest.getImageAttachment().setIdCardImage(
                    Methods.encodeBitmapToBase64String(getIntent().getStringExtra("path1")));
            mFinalRequest.getImageAttachment().setDriverLicenseImage(
                    Methods.encodeBitmapToBase64String(getIntent().getStringExtra("path2")));
            if (getIntent().getStringExtra("path3").length() > 0)
                mFinalRequest.getImageAttachment().setPrevDriverCardImage(
                        Methods.encodeBitmapToBase64String(getIntent().getStringExtra("path3")));
            
            mSendButton.setOnClickListener(v -> mPresenter.createRequest(mFinalRequest));
        } else
        {
            mSendButton.setVisibility(View.GONE);
            if (getIntent().getBooleanExtra("admin", false))
            {
                mAdminToolbar.setVisibility(View.VISIBLE);
                
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.status_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mAdminPanelSpinner.setAdapter(adapter);
                mAdminPanelSpinner.setSelection(0, false);
                mAdminPanelSpinner.setOnItemSelectedListener(this);
                mAdminPanelStatusText.append(mFinalRequest.getStatus());
            }
        }
        
        setTextViews();
        setImageViews();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        mPresenter.subscribe(this);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        
        mPresenter.unsubscribe();
    }
    
    @SuppressLint("SimpleDateFormat")
    @Override
    public void setTextViews()
    {
        mEgnText.setText(String.format("EGN: %s", mFinalRequest.getApplicantDetails().getEgn()));
        mFirstNameText.setText(String.format("First name: %s", mFinalRequest.getApplicantDetails().getFirstName()));
        mMiddleNameText.setText(String.format("Middle name: %s", mFinalRequest.getApplicantDetails().getMiddleName()));
        mLastNameText.setText(String.format("Last name: %s", mFinalRequest.getApplicantDetails().getLastName()));
        mBirthdateText.setText(String.format("Birthdate: %s", mFinalRequest.getApplicantDetails().getBirthDate()));
        mAddressText.setText(String.format("Address: %s", mFinalRequest.getApplicantDetails().getAddress()));
        mPhoneNumText.setText(String.format("Phone number: %s", mFinalRequest.getApplicantDetails().getPhoneNumber()));
        mEmailText.setText(String.format("Email: %s", mFinalRequest.getApplicantDetails().getEmail()));
    }
    
    @Override
    public void setImageViews()
    {
        if (mFinalRequest.getImageAttachment().getSelfieImage() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getSelfieImage()))
                    .into(mSelfieImage);
        
        if (mFinalRequest.getImageAttachment().getSignatureScreenshot() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getSignatureScreenshot()))
                    .into(mSignatureImage);
        
        if (mFinalRequest.getImageAttachment().getIdCardImage() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getIdCardImage()))
                    .into(mIdCardImage);
        else
            mIdCardImage.setVisibility(View.GONE);
        
        if (mFinalRequest.getImageAttachment().getDriverLicenseImage() != null)
            Glide.with(this)
                    .load(Methods.decodeBitmapFromBase64String(
                            mFinalRequest.getImageAttachment().getDriverLicenseImage()))
                    .into(mDrivingLicImage);
        else
            mDrivingLicImage.setVisibility(View.GONE);
    }
    
    public static void setRequest(BaseRequest request)
    {
        mFinalRequest = request;
    }
    
    @Override
    public void setPresenter(RequestPreviewContracts.Presenter presenter)
    {
        mPresenter = presenter;
    }
    
    @Override
    public void finalizeRequestReview(BaseRequest baseRequest)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("request_id", baseRequest.getId());
        returnIntent.putExtra("request_status", baseRequest.getStatus());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public Activity getActivity()
    {
        return this;
    }
    
    @Override
    public void showProgressBar()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideProgressBar()
    {
        mProgressBar.setVisibility(View.GONE);
    }
    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String status = "Request status: " + parent.getItemAtPosition(position).toString();
        String lastKnownStatus = mAdminPanelStatusText.getText().toString();
        
        if (status.equals(lastKnownStatus))
            Methods.showCrouton(this,
                    "Request already has the same status",
                    Style.ALERT, false);
        
        mAdminPanelStatusText.setText(String.format(
                "Request status: %s", parent.getItemAtPosition(position)));
        
        switch (parent.getItemAtPosition(position).toString())
        {
            case "pending":
                mFinalRequest.setStatus(RequestStatus.PENDING.toString());
                break;
            case "approved":
                mFinalRequest.setStatus(RequestStatus.APPROVED.toString());
                break;
            case "disapproved":
                mFinalRequest.setStatus(RequestStatus.DISAPPROVED.toString());
                break;
        }
        
        mPresenter.updateStatus(mFinalRequest);
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    
    }
    
    @Override
    public void navigateToHome()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
