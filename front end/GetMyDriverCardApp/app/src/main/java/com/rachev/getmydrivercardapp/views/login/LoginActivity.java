package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.UserDTO;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.BCrypt;
import com.rachev.getmydrivercardapp.utils.Constants;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import studios.codelight.smartloginlibrary.*;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;
import studios.codelight.smartloginlibrary.util.SmartLoginException;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, SmartLoginCallbacks
{
    //public static final int IDENTIFIER = 160;
    
    private SmartUser mCurrentUser;
    private SmartLoginConfig mSmartLoginConfig;
    private SmartLogin mSmartLogin;
    private AlertDialog mAlertDialog;
    private UsersService mUsersService;
    private SchedulerProvider mSchedulerProvider;
    private static long mBackPressedTime;
    private static String mCurrentUsername;
    
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        ButterKnife.bind(this);
        
        mSmartLoginConfig = new SmartLoginConfig(this, this);
        mSmartLoginConfig.setFacebookAppId(getString(R.string.facebook_app_id));
        
        mUsersService = GetMyDriverCardApplication.getUsersService();
        mSchedulerProvider = GetMyDriverCardApplication.getSchedulerProvider();
        
        mCustomLoginButton.setOnClickListener(this);
        mCustomSignupButton.setOnClickListener(this);
        mGoogleLoginButton.setOnClickListener(this);
        mFacebookLoginButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        
        mProfileSection.setVisibility(View.GONE);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        mCurrentUser = UserSessionManager.getCurrentUser(this);
        updateUi();

//        if (getIntent().getBooleanExtra(Constants.IS_OPENED_FROM_DRAWER, false))
//        {
//            setProfileData(mCurrentUser);
//            updateUi();
//            return;
//        }
        
        if (mCurrentUser != null)
        {
            setProfileData(mCurrentUser);
            navigateToHome();
        }
    }
    
    @Override
    public void onBackPressed()
    {
        if (mBackPressedTime + Constants.BACK_PRESS_PERIOD > System.currentTimeMillis())
            super.onBackPressed();
        else
        {
            showToast(Constants.SECOND_PRESS_POPUP_TOAST, false);
            mBackPressedTime = System.currentTimeMillis();
        }
    }
    
    @Override
    public void onLoginSuccess(SmartUser user)
    {
        setProfileData(user);
        updateUi();
        navigateToHome();
        
        showToast(Constants.USER_LOGGED_IN_TOAST, false);
    }
    
    private void setProfileData(SmartUser user)
    {
        String name = null;
        String profilePicUrl = null;
        
        if (user instanceof SmartGoogleUser)
        {
            name = ((SmartGoogleUser) user).getDisplayName();
            profilePicUrl = ((SmartGoogleUser) user).getPhotoUrl();
        } else if (user instanceof SmartFacebookUser)
        {
            name = ((SmartFacebookUser) user).getProfileName();
            profilePicUrl = Constants.FB_GRAPH_PROFILE_PIC_URL_PART1 + user.getUserId() +
                    Constants.FB_GRAPH_PROFILE_PIC_URL_PART2;
        } else
            name = user.getUsername();
        
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
        showToast(e.getMessage(), true);
    }
    
    @Override
    public SmartUser doCustomLogin()
    {
        SmartUser customUser = new SmartUser();
        customUser.setUsername(mCurrentUsername);
        
        return customUser;
    }
    
    @Override
    public SmartUser doCustomSignup()
    {
        SmartUser customUser = new SmartUser();
        customUser.setUsername(mCurrentUsername);
        
        return customUser;
    }
    
    private void hideKeyboard()
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void showKeyboard()
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(getCurrentFocus().getRootView(), 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void showToast(String message, boolean important)
    {
        hideKeyboard();
        Toast.makeText(this,
                message,
                important ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
                .show();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.custom_signin_button:
            {
                String username = mCustomLoginUsername.getText().toString();
                String password = mCustomLoginPassword.getText().toString();
                
                if (isPasswordCorrectIfUserExists(username, password))
                    mCurrentUsername = username;
                
                if (mCurrentUsername != null)
                {
                    mSmartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                    mSmartLogin.login(mSmartLoginConfig);
                } else
                {
                    if (!username.isEmpty() && !password.isEmpty())
                        showToast(Constants.WRONG_USERNAME_OR_PASSWORD_TOAST, true);
                }
            }
            break;
            case R.id.custom_signup_button:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                
                @SuppressLint("InflateParams")
                View mView = getLayoutInflater()
                        .inflate(R.layout.dialog_user_signup, null);
                
                final EditText mUsername = mView.findViewById(R.id.et_username);
                final EditText mPassword = mView.findViewById(R.id.et_password);
                final EditText mConfirmPassword = mView.findViewById(R.id.et_confirm_password);
                Button mAddButton = mView.findViewById(R.id.btn_add);
                Button mCancelButton = mView.findViewById(R.id.btn_cancel);
                
                showKeyboard();
                mAddButton.setOnClickListener(v1 ->
                {
                    routePostUserCreationData(
                            mUsername.getText().toString(),
                            mPassword.getText().toString(),
                            mConfirmPassword.getText().toString());
                    
                    hideKeyboard();
                    mAlertDialog.dismiss();
                });
                
                mCancelButton.setOnClickListener(v2 ->
                {
                    hideKeyboard();
                    mAlertDialog.dismiss();
                });
                
                mBuilder.setView(mView);
                mAlertDialog = mBuilder.create();
                mAlertDialog.show();
                
                if (!mAlertDialog.isShowing())
                {
                    mSmartLogin = SmartLoginFactory.build(LoginType.CustomSignup);
                    mSmartLogin.signup(mSmartLoginConfig);
                }
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
                    {
                        mSmartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                        mCurrentUsername = null;
                    }
                    
                    if (mSmartLogin.logout(getApplicationContext()))
                    {
                        updateUi();
                        showToast(Constants.USER_LOGGED_OUT_TOAST, false);
                    }
                }
                navigateToHome();
                break;
        }
    }
    
    private void createUser(UserDTO user)
    {
        Observable.create((ObservableOnSubscribe<UserDTO>) emitter ->
        {
            try
            {
                mUsersService.createUser(user);
                emitter.onComplete();
            } catch (Exception e)
            {
                emitter.onError(e);
            }
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        msg -> showToast(Constants.USER_SIGNED_UP_TOAST, true),
                        err -> showToast(err.getMessage(), true));
    }
    
    private void routePostUserCreationData(String username, String password, String confirmedPassword)
    {
        if (!password.equals(confirmedPassword))
        {
            showToast(Constants.PASSWORDS_NO_MATCH_TOAST, true);
            return;
        }
        
        if (username.isEmpty() || password.isEmpty())
        {
            showToast(Constants.NOT_ALL_FIELDS_FILLED_TOAST, true);
            return;
        }
        
        createUser(new UserDTO(username.toLowerCase(), BCrypt.hashpw(password, BCrypt.gensalt())));
    }
    
    private boolean isPasswordCorrectIfUserExists(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
        {
            showToast(Constants.NOT_ALL_FIELDS_FILLED_TOAST, true);
            return false;
        }
        
        try
        {
            UserDTO user = mUsersService.getByUsername(username);
            if (user != null)
                if (BCrypt.checkpw(password, user.getPassword()))
                    return true;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return false;
    }
    
    private void updateUi()
    {
        mCurrentUser = UserSessionManager.getCurrentUser(this);
        
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
            mCustomLoginUsername.setText(new String(), TextView.BufferType.NORMAL);
            mCustomLoginPassword.setText(new String(), TextView.BufferType.NORMAL);
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
    
    public void navigateToHome()
    {
        if (mCurrentUser != null)
        {
        
        } else
        {
        
        }
    }
}
