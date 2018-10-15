package com.rachev.getmydrivercardapp.views.login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import studios.codelight.smartloginlibrary.*;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, SmartLoginCallbacks
{
    //public static final int IDENTIFIER = 160;
    
    private SmartUser mCurrentUser;
    private SmartLoginConfig mSmartLoginConfig;
    private SmartLogin mSmartLogin;
    
    @BindView(R.id.email_edittext)
    EditText mCustomLoginEmail;
    
    @BindView(R.id.password_edittext)
    EditText mCustomLoginPassword;
    
    @BindView(R.id.custom_signin_button)
    Button mCustomLoginButton;
    
    @BindView(R.id.custom_signup_button)
    Button mCustomSignupButton;
    
    @BindView(R.id.facebook_login_button)
    Button mFacebookLoginButton;
    
    @BindView(R.id.google_login_button)
    Button mGoogleLoginButton;
    
    @BindView(R.id.btn_logout)
    Button mLogoutButton;
    
    @BindView(R.id.profile_name)
    TextView mProfileNameTextView;
    
    @BindView(R.id.profile_section)
    LinearLayout mProfileSection;
    
    @BindView(R.id.profile_pic)
    ImageView mProfilePictureView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        ButterKnife.bind(this);
        
        mSmartLoginConfig = new SmartLoginConfig(this, this);
        mSmartLoginConfig.setFacebookAppId(getString(R.string.facebook_app_id));
        
        mCustomLoginButton.setOnClickListener(this);
        mCustomSignupButton.setOnClickListener(this);
        mGoogleLoginButton.setOnClickListener(this);
        mFacebookLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        
        mProfileSection.setVisibility(View.GONE);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        mCurrentUser = UserSessionManager.getCurrentUser(this);
        
        if (getIntent().getBooleanExtra(Constants.IS_OPENED_FROM_DRAWER, false))
        {
            setProfileData(mCurrentUser);
            updateUi();
            return;
        }
        
        if (mCurrentUser != null)
        {
            setProfileData(mCurrentUser);
            //navigateToHome();
        }
    }
    
    @Override
    public void onLoginSuccess(SmartUser user)
    {
        setProfileData(user);
        updateUi();
        //navigateToHome();
        
        Toast.makeText(this,
                Constants.USER_LOGGED_IN_TOAST,
                Toast.LENGTH_SHORT)
                .show();
    }
    
//    private SocialUser getSocialUserObj(SmartUser user)
//    {
//        SocialUser socialUser = new SocialUser(user.getUserId());
//
//        if (user instanceof SmartGoogleUser)
//            socialUser.setSocialMedia("google");
//        else
//            socialUser.setSocialMedia("facebook");
//
//        return socialUser;
//    }
    
    private void setProfileData(SmartUser user)
    {
        String name = null;
        String profilePicUrl = null;
        
        if (user instanceof SmartGoogleUser)
        {
            name = user.getEmail();
            profilePicUrl = ((SmartGoogleUser) user).getPhotoUrl();
        } else if (user instanceof SmartFacebookUser)
        {
            name = ((SmartFacebookUser) user).getProfileName();
            profilePicUrl = Constants.FB_GRAPH_PROFILE_PIC_URL_PART1 + user.getUserId() +
                    Constants.FB_GRAPH_PROFILE_PIC_URL_PART2;
        }
        
        if (profilePicUrl == null)
            profilePicUrl = Constants.NO_PROFILE_PIC_AVATAR_URL;
        
        mProfileNameTextView.setText(name);
        Glide.with(this)
                .load(profilePicUrl)
                .into(mProfilePictureView);
    }
    
    @Override
    public void onLoginFailure(SmartLoginException e)
    {
        Toast.makeText(this,
                e.getMessage(),
                Toast.LENGTH_LONG)
                .show();
    }
    
    @Override
    public SmartUser doCustomLogin()
    {
        return null;
    }
    
    @Override
    public SmartUser doCustomSignup()
    {
        return null;
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.google_login_button:
                mSmartLogin = SmartLoginFactory.build(LoginType.Google);
                mSmartLogin.login(mSmartLoginConfig);
                break;
            case R.id.facebook_login_button:
                mSmartLogin = SmartLoginFactory.build(LoginType.Facebook);
                mSmartLogin.login(mSmartLoginConfig);
                break;
            case R.id.btn_logout:
                if (mCurrentUser != null)
                {
                    if (mCurrentUser instanceof SmartGoogleUser)
                        mSmartLogin = SmartLoginFactory.build(LoginType.Google);
                    else if (mCurrentUser instanceof SmartFacebookUser)
                        mSmartLogin = SmartLoginFactory.build(LoginType.Facebook);
                    
                    if (mSmartLogin.logout(this))
                    {
                        updateUi();
                        Toast.makeText(this,
                                Constants.USER_LOGGED_OUT_TOAST,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                //navigateToHome();
                break;
        }
    }
    
    private void updateUi()
    {
        mCurrentUser = UserSessionManager.getCurrentUser(this);
        
        if (mCurrentUser != null)
        {
            mProfileSection.setVisibility(View.VISIBLE);
            mCustomLoginEmail.setVisibility(View.GONE);
            mCustomLoginPassword.setVisibility(View.GONE);
            mCustomLoginButton.setVisibility(View.GONE);
            mCustomSignupButton.setVisibility(View.GONE);
            mFacebookLoginButton.setVisibility(View.GONE);
            mGoogleLoginButton.setVisibility(View.GONE);
        } else
        {
            mProfileSection.setVisibility(View.GONE);
            mCustomLoginEmail.setVisibility(View.VISIBLE);
            mCustomLoginPassword.setVisibility(View.VISIBLE);
            mCustomLoginButton.setVisibility(View.VISIBLE);
            mCustomSignupButton.setVisibility(View.VISIBLE);
            mFacebookLoginButton.setVisibility(View.VISIBLE);
            mGoogleLoginButton.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        mSmartLogin.onActivityResult(requestCode, resultCode, data, mSmartLoginConfig);
    }
    
//    @Override
//    protected long getIdentifier()
//    {
//        return IDENTIFIER;
//    }

//    public void navigateToHome()
//    {
//        if (mCurrentUser != null)
//        {
//            Intent intent = new Intent(this, PasswordsListActivity.class);
//            intent.putExtra(Constants.USER_OBJ_EXTRA, getSocialUserObj(mCurrentUser));
//            startActivity(intent);
//            finish();
//        } else
//        {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }
//    }
}
