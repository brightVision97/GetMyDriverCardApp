package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.home.HomeActivity;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.*;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, SmartLoginCallbacks, LoginContracts.View, LoginContracts.Navigator
{
    private SmartUser mCurrentUser;
    private SmartLoginConfig mSmartLoginConfig;
    private SmartLogin mSmartLogin;
    private AlertDialog mAlertDialog;
    private LoginContracts.Presenter mPresenter;
    private LoginContracts.Navigator mNavigator;
    private AwesomeValidation mAwesomeValidation;
    private User mUser;
    private static long mBackPressedTimes;
    
    @BindView(R.id.username_edittext)
    EditText mCustomLoginUsername;
    
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
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        
        mPresenter = new LoginPresenter(this);
        mPresenter.setNavigator(this);
        
        ButterKnife.bind(this, this);
        
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        mAwesomeValidation.setContext(this);
        
        mSmartLoginConfig = new SmartLoginConfig(this, this);
        mSmartLoginConfig.setFacebookAppId(getString(R.string.facebook_app_id));
        
        mCustomLoginButton.setOnClickListener(this);
        mCustomSignupButton.setOnClickListener(this);
        mGoogleLoginButton.setOnClickListener(this);
        mFacebookLoginButton.setOnClickListener(this);
    }
    
    @Override
    public void onBackPressed()
    {
        if (mBackPressedTimes + Constants.Integers.BACK_PRESS_PERIOD > System.currentTimeMillis())
            super.onBackPressed();
        else
        {
            Methods.showCrouton(this,
                    Constants.Strings.SECOND_BACK_PRESS_TO_EXIT,
                    Style.INFO, false);
            mBackPressedTimes = System.currentTimeMillis();
        }
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        mPresenter.subscribe(this);
        mCurrentUser = UserSessionManager.getCurrentUser(this);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        
        mPresenter.unsubscribe();
    }
    
    @Override
    public void onLoginSuccess(SmartUser user)
    {
        if (user instanceof SmartGoogleUser || user instanceof SmartFacebookUser)
            mPresenter.createUser(Methods.getUserFromSmartUser(user));
    }
    
    @Override
    public void onLoginFailure(SmartLoginException e)
    {
        Methods.showCrouton(this, e.getMessage(), Style.ALERT, true);
    }
    
    @Override
    public SmartUser doCustomLogin()
    {
        mPresenter.login(
                mCustomLoginUsername.getText().toString(),
                mCustomLoginPassword.getText().toString());
        SmartUser customUser = new SmartUser();
        customUser.setUsername(mCustomLoginUsername.getText().toString());
        
        return customUser;
    }
    
    @Override
    public SmartUser doCustomSignup()
    {
        SmartUser customUser = new SmartUser();
        customUser.setUsername(mCustomLoginUsername.getText().toString());
        
        return customUser;
    }
    
    @Override
    public void setUser(User user)
    {
        mUser = user;
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.custom_signin_button:
                mSmartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                mSmartLogin.login(mSmartLoginConfig);
                break;
            case R.id.custom_signup_button:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                
                @SuppressLint("InflateParams")
                View mView = getLayoutInflater().inflate(
                        R.layout.dialog_user_signup,
                        null);
                
                final EditText mUsernameEditText = mView.findViewById(R.id.et_username);
                final EditText mPasswordEditText = mView.findViewById(R.id.et_password);
                final EditText mConfirmPasswordEditText = mView.findViewById(R.id.et_confirm_password);
                Button mUserAddButton = mView.findViewById(R.id.btn_add);
                Button mSignupCancelButton = mView.findViewById(R.id.btn_cancel);
                
                mAwesomeValidation.addValidation(mUsernameEditText, "[a-zA-Z0-9_-]+", "Valid username please");
                mAwesomeValidation.addValidation(mPasswordEditText,
                        "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{6,}",
                        "The password should contain at least 6 characters including at least one lower case letter," +
                                " one upper case letter, one number and one punctuation mark");
                mAwesomeValidation.addValidation(mConfirmPasswordEditText, mPasswordEditText, "Please re-enter password");
                
                mUserAddButton.setOnClickListener(v1 ->
                {
                    if (mAwesomeValidation.validate())
                        mPresenter.createUser(new User(
                                mUsernameEditText.getText().toString(),
                                mPasswordEditText.getText().toString()));
                    
                    Methods.hideKeyboard(this);
                });
                
                mSignupCancelButton.setOnClickListener(v2 ->
                {
                    Methods.hideKeyboard(this);
                    mAlertDialog.dismiss();
                });
                
                mBuilder.setView(mView);
                mAlertDialog = mBuilder.create();
                mAlertDialog.show();
                break;
            case R.id.google_login_button:
                mSmartLogin = SmartLoginFactory.build(LoginType.Google);
                mSmartLogin.login(mSmartLoginConfig);
                break;
            case R.id.facebook_login_button:
                mSmartLogin = SmartLoginFactory.build(LoginType.Facebook);
                mSmartLogin.login(mSmartLoginConfig);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void performLogin()
    {
        mSmartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
        mSmartLogin.login(mSmartLoginConfig);
    }
    
    @Override
    public void dismissSignupDialog()
    {
        mAlertDialog.dismiss();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        mSmartLogin.onActivityResult(requestCode, resultCode, data, mSmartLoginConfig);
    }
    
    @Override
    public void setPresenter(LoginContracts.Presenter presenter)
    {
        mPresenter = presenter;
    }
    
    public void setNavigator(LoginContracts.Navigator navigator)
    {
        mNavigator = navigator;
    }
    
    @Override
    public void navigateToHome(User user)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("hasLoggedIn", true);
        intent.putExtra("user", user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}