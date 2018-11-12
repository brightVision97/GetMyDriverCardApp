package com.rachev.getmydrivercardapp.views.cardrequest.applicantdetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.ApplicantDetails;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.utils.enums.Reasons;
import com.rachev.getmydrivercardapp.utils.enums.RequestStatus;
import com.rachev.getmydrivercardapp.views.photos.SelfiePickingActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BaseApplicantDetailsActivity extends AppCompatActivity implements View.OnClickListener
{
    private AwesomeValidation mAwesomeValidation;
    private RequestStatus mRequestStatus;
    private Reasons.Renewal mRenewalReason;
    private Reasons.Replacement mReplacementReason;
    private String mTachCardIssuingCountry;
    private String mTachCardNumber;
    private String mDrivingLicIssuingCountry;
    private String mDrivingLicNumber;
    private String mReplacementIncidentDate;
    private String mReplacementIncidentPlace;
    
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    
    @BindView(R.id.egn_edittext)
    EditText mEgnEditText;
    
    @BindView(R.id.birthdate_edittext)
    EditText mBirthdateEditText;
    
    @BindView(R.id.first_name_edittext)
    EditText mFirstNameEditText;
    
    @BindView(R.id.middle_name_edittext)
    EditText mMiddleNameEditText;
    
    @BindView(R.id.last_name_edittext)
    EditText mLastNameEditText;
    
    @BindView(R.id.address_edittext)
    EditText mAddressEditText;
    
    @BindView(R.id.phone_num_edittext)
    EditText mPhoneNumberEditText;
    
    @BindView(R.id.email_edittext)
    EditText mEmailEditText;
    
    @BindView(R.id.btn_next)
    Button mNextButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);
        setTitle("Step 1");
        
        ButterKnife.bind(this);
        
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        mAwesomeValidation.setContext(this);
        
        mNextButton.setOnClickListener(this);
        
        addValidations(this);
    }
    
    private void handleAllExtras()
    {
        switch (getIntent().getStringExtra("card_type"))
        {
            case "new":
                break;
            case "renew":
                this.mRenewalReason = Reasons.Renewal.valueOf(
                        getIntent().getStringExtra("renewal_reason"));
                break;
            case "replace":
                String replacementReason = getIntent().getStringExtra("replacement_reason");
                this.mReplacementReason = Reasons.Replacement.valueOf(replacementReason);
                
                if (replacementReason.equals("exchange_for_bg_card"))
                {
                    String[] extras = getIntent().getStringArrayExtra("exchange_extras");
                    this.mTachCardIssuingCountry = extras[0];
                    this.mTachCardNumber = extras[1];
                    this.mDrivingLicIssuingCountry = extras[2];
                    this.mDrivingLicNumber = extras[3];
                } else if (replacementReason.equals("lost") || replacementReason.equals("stolen"))
                {
                    String[] extras = getIntent().getStringArrayExtra("incident_extras");
                    this.mReplacementIncidentDate = extras[0];
                    this.mReplacementIncidentPlace = extras[1];
                }
                break;
            default:
                break;
        }
    }
    
    @SuppressLint("SimpleDateFormat")
    private void addValidations(Activity activity)
    {
        mAwesomeValidation.addValidation(this, R.id.egn_edittext, validationHolder ->
        {
            if (!Methods.isEgnValid(mEgnEditText.getText().toString()))
                return false;
            return true;
        }, validationHolder ->
        {
            TextView textViewError = (TextView) validationHolder.getView();
            textViewError.setError(validationHolder.getErrMsg());
            textViewError.setTextColor(Color.RED);
        }, validationHolder ->
        {
            TextView textViewError = (TextView) validationHolder.getView();
            textViewError.setError(null);
            textViewError.setTextColor(Color.GREEN);
        }, R.string.egn_validity_err);
        mAwesomeValidation.addValidation(mFirstNameEditText, "^[a-zA-Z ]*$", "Please use only letters");
        mAwesomeValidation.addValidation(mMiddleNameEditText, "^[a-zA-Z ]*$", "Please use only letters");
        mAwesomeValidation.addValidation(mLastNameEditText, "^[a-zA-Z ]*$", "Please use only letters");
        mAwesomeValidation.addValidation(mAddressEditText, "^[#.0-9a-zA-Z\\s,-]+$", "Invalid address formatting");
        mAwesomeValidation.addValidation(mPhoneNumberEditText, "\\d{10}|(?:\\d{3}-){2}\\d{4}", "Valid numbers are 1234567890 or 123-456-7890");
        mAwesomeValidation.addValidation(mEmailEditText, Patterns.EMAIL_ADDRESS, "Valid email please");
        mAwesomeValidation.addValidation(mBirthdateEditText, input ->
        {
            try
            {
                Calendar calendarBirthday = Calendar.getInstance();
                Calendar calendarToday = Calendar.getInstance();
                
                calendarBirthday.setTime(new SimpleDateFormat(
                        "dd/MM/yyyy", Locale.US)
                        .parse(input));
                int yearOfToday = calendarToday.get(Calendar.YEAR);
                int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
                if (yearOfToday - yearOfBirthday > 18)
                    return true;
                else if (yearOfToday - yearOfBirthday == 18)
                {
                    int monthOfToday = calendarToday.get(Calendar.MONTH);
                    int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                    if (monthOfToday > monthOfBirthday)
                        return true;
                    else if (monthOfToday == monthOfBirthday)
                    {
                        if (calendarToday.get(Calendar.DAY_OF_MONTH) >=
                                calendarBirthday.get(Calendar.DAY_OF_MONTH))
                            return true;
                    }
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            return false;
        }, "Birthdate should be like Day/Month/Year, and must be >= 18");
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_next:
                if (mAwesomeValidation.validate())
                {
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                    ApplicantDetails details = new ApplicantDetails(
                            mEgnEditText.getText().toString(),
                            mBirthdateEditText.getText().toString(),
                            mFirstNameEditText.getText().toString(),
                            mMiddleNameEditText.getText().toString(),
                            mLastNameEditText.getText().toString(),
                            mAddressEditText.getText().toString(),
                            mEmailEditText.getText().toString(),
                            mPhoneNumberEditText.getText().toString());
                    
                    BaseRequest baseRequest = new BaseRequest(
                            (User) getIntent().getSerializableExtra("user"),
                            getIntent().getStringExtra("card_type"),
                            RequestStatus.PENDING.toString(),
                            details);
                    
                    if (mRenewalReason != null)
                        baseRequest.setRenewalReason(mRenewalReason.toString());
                    if (mReplacementReason != null)
                        baseRequest.setReplacementReason(mReplacementReason.toString());
                    baseRequest.setTachCardIssuingCountry(mTachCardIssuingCountry);
                    baseRequest.setTachCardNumber(mTachCardNumber);
                    baseRequest.setDrivingLicIssuingCountry(mDrivingLicIssuingCountry);
                    baseRequest.setDrivingLicNumber(mDrivingLicNumber);
                    baseRequest.setReplacementIncidentDate(mReplacementIncidentDate);
                    baseRequest.setReplacementIncidentPlace(mReplacementIncidentPlace);
                    
                    Intent intent = new Intent(this, SelfiePickingActivity.class);
                    intent.putExtra("request", baseRequest);
                    startActivity(intent);
                }
                break;
        }
    }
}
