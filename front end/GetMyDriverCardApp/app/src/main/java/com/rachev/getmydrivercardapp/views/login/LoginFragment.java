package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.*;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment
        implements View.OnClickListener, SmartLoginCallbacks, LoginContracts.View
{
    private SmartUser mCurrentUser;
    private SmartLoginConfig mSmartLoginConfig;
    private SmartLogin mSmartLogin;
    private AlertDialog mAlertDialog;
    private LoginContracts.Presenter mPresenter;
    private LoginContracts.Navigator mNavigator;
    private boolean mIsUserAuthorized;
    
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
    
    @BindView(R.id.btn_logout)
    Button mLogoutButton;
    
    @BindView(R.id.profile_name)
    TextView mProfileNameTextView;
    
    @BindView(R.id.profile_section)
    LinearLayout mProfileSection;
    
    @BindView(R.id.profile_pic)
    ImageView mProfilePictureView;
    
    public LoginFragment()
    {
        // Required empty public constructor
    }
    
    public static LoginFragment getInstance()
    {
        return new LoginFragment();
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        
        ButterKnife.bind(this, view);
        
        mSmartLoginConfig = new SmartLoginConfig(getActivity(), this);
        mSmartLoginConfig.setFacebookAppId(getString(R.string.facebook_app_id));
        
        mCustomLoginButton.setOnClickListener(this);
        mCustomSignupButton.setOnClickListener(this);
        mGoogleLoginButton.setOnClickListener(this);
        mFacebookLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        
        mProfileSection.setVisibility(View.GONE);
        
        return view;
    }
    
    @Override
    public void onStart()
    {
        super.onStart();
        
        if (UserSessionManager.getCurrentUser(getContext()) != null)
            mIsUserAuthorized = true;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        mPresenter.subscribe(this);
        mCurrentUser = UserSessionManager.getCurrentUser(getContext());
        
        if (mCurrentUser != null)
            setProfileData(mCurrentUser);
        
        navigateToHome();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        
        mPresenter.unsubscribe();
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        
        Crouton.cancelAllCroutons();
    }
    
    @Override
    public void onLoginSuccess(SmartUser user)
    {
        mIsUserAuthorized = true;
        
        if (user instanceof SmartGoogleUser || user instanceof SmartFacebookUser)
        {
            mPresenter.prepareAndSendSocialUserDbEntry(user);
            showCrouton(Constants.Strings.USER_LOGGED_IN, Style.CONFIRM, false);
        }
        
        setProfileData(user);
        navigateToHome();
    }
    
    private void setProfileData(SmartUser user)
    {
        String name = user.getUsername();
        String profilePicUrl = Constants.Strings.BLANK_PROFILE_PIC_IMG_URL;
        
        if (user instanceof SmartGoogleUser)
        {
            name = ((SmartGoogleUser) user).getDisplayName();
            profilePicUrl = ((SmartGoogleUser) user).getPhotoUrl();
        } else if (user instanceof SmartFacebookUser)
        {
            name = ((SmartFacebookUser) user).getProfileName();
            profilePicUrl = Constants.Strings.FB_PROFILE_PIC_URL_PRE_ID
                    + user.getUserId()
                    + Constants.Strings.FB_PROFILE_PIC_URL_POST_ID;
        }
        
        mProfileNameTextView.setText(name);
        Glide.with(this)
                .load(profilePicUrl)
                .into(mProfilePictureView);
    }
    
    @Override
    public void onLoginFailure(SmartLoginException e)
    {
        showCrouton(e.getMessage(), Style.ALERT, true);
    }
    
    @Override
    public SmartUser doCustomLogin()
    {
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
    
    private void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    private void hideKeyboardFrom(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    @Override
    public void showCrouton(String message, Style style, boolean important)
    {
        hideKeyboard(getActivity());
        Crouton.makeText(getActivity(), message,
                new Style.Builder(style)
                        .setHeight(Constants.Integers.CROUTON_HEIGHT)
                        .setTextSize(Constants.Integers.CROUTON_TEXT_SIZE)
                        .build())
                .setConfiguration(new Configuration.Builder()
                        .setDuration(important
                                ? Constants.Integers.CROUTON_LONG_DURATION
                                : Constants.Integers.CROUTON_SHORT_DURATION)
                        .build())
                .show();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.custom_signin_button:
                mPresenter.fetchSecuredResourcesOnLogin(
                        mCustomLoginUsername.getText().toString(),
                        mCustomLoginPassword.getText().toString());
                break;
            case R.id.custom_signup_button:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                
                @SuppressLint("InflateParams")
                View mView = getLayoutInflater().inflate(
                        R.layout.dialog_user_signup,
                        null);
                
                final EditText mUsername = mView.findViewById(R.id.et_username);
                final EditText mPassword = mView.findViewById(R.id.et_password);
                final EditText mConfirmPassword = mView.findViewById(R.id.et_confirm_password);
                Button mAddButton = mView.findViewById(R.id.btn_add);
                Button mCancelButton = mView.findViewById(R.id.btn_cancel);
                
                mAddButton.setOnClickListener(v1 ->
                {
                    mPresenter.routePostUserCreationData(
                            mUsername.getText().toString(),
                            mPassword.getText().toString(),
                            mConfirmPassword.getText().toString());
                    
                    hideKeyboard(getActivity());
                });
                
                mCancelButton.setOnClickListener(v2 ->
                {
                    hideKeyboard(getActivity());
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
            case R.id.btn_logout:
                if (mCurrentUser != null)
                {
                    if (mCurrentUser instanceof SmartFacebookUser)
                        mSmartLogin = SmartLoginFactory.build(LoginType.Facebook);
                    else if (mCurrentUser instanceof SmartGoogleUser)
                        mSmartLogin = SmartLoginFactory.build(LoginType.Google);
                    else
                        mSmartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                    
                    if (mSmartLogin.logout(getContext()) && mIsUserAuthorized)
                        showCrouton(Constants.Strings.USER_LOGGED_OUT, Style.INFO, false);
                }
                navigateToHome();
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
    public void performLogout()
    {
        mIsUserAuthorized = false;
        mLogoutButton.performClick();
    }
    
    @Override
    public void dismissSignupDialog()
    {
        mAlertDialog.dismiss();
    }
    
    private void updateUi()
    {
        mCurrentUser = UserSessionManager.getCurrentUser(getContext());
        
        hideKeyboard(getActivity());
        if (mCurrentUser != null)
        {
            mProfileSection.setVisibility(View.VISIBLE);
            mCustomLoginUsername.setVisibility(View.GONE);
            mCustomLoginPassword.setVisibility(View.GONE);
            mCustomLoginButton.setVisibility(View.GONE);
            mCustomSignupButton.setVisibility(View.GONE);
            mFacebookLoginButton.setVisibility(View.GONE);
            mGoogleLoginButton.setVisibility(View.GONE);
        } else
        {
            mCustomLoginUsername.setText("", TextView.BufferType.NORMAL);
            mCustomLoginPassword.setText("", TextView.BufferType.NORMAL);
            mProfileSection.setVisibility(View.GONE);
            mCustomLoginUsername.setVisibility(View.VISIBLE);
            mCustomLoginPassword.setVisibility(View.VISIBLE);
            mCustomLoginButton.setVisibility(View.VISIBLE);
            mCustomSignupButton.setVisibility(View.VISIBLE);
            mFacebookLoginButton.setVisibility(View.VISIBLE);
            mGoogleLoginButton.setVisibility(View.VISIBLE);
        }
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
    public void navigateToHome()
    {
        updateUi();
        mNavigator.navigateToHome();
    }
}
