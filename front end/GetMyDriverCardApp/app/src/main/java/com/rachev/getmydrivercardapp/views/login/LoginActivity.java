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
import io.reactivex.disposables.Disposable;
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
    private static String mEmailAddress;
    private String mBackgroundFilename;
    private UsersService mUsersService;
    private SchedulerProvider mSchedulerProvider;
    
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
            //navigateToHome();
        }
    }
    
    @Override
    public void onLoginSuccess(SmartUser user)
    {
        hideKeyboard();
        
        if (user instanceof SmartGoogleUser || user instanceof SmartFacebookUser)
            createUser(getSocialUserDTO(user));
        
        setProfileData(user);
        updateUi();
        //navigateToHome();
        
        showToast(Constants.USER_LOGGED_IN_TOAST);
    }
    
    private UserDTO getSocialUserDTO(SmartUser user)
    {
        UserDTO userDTO = new UserDTO(user.getUserId());
        
        if (user instanceof SmartGoogleUser)
            userDTO.setLoginType(Constants.LOGIN_TYPE_GOOGLE);
        else
            userDTO.setLoginType(Constants.LOGIN_TYPE_FACEBOOK);
        
        return userDTO;
    }
    
    private void hideKeyboard()
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e)
        {
            showToast(e.getMessage());
        }
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
            name = user.getEmail();
        
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
        showToast(e.getMessage());
    }
    
    @Override
    public SmartUser doCustomLogin()
    {
        SmartUser customUser = new SmartUser();
        customUser.setEmail(mEmailAddress);
        
        return customUser;
    }
    
    @Override
    public SmartUser doCustomSignup()
    {
        SmartUser customUser = new SmartUser();
        customUser.setEmail(mEmailAddress);
        
        return customUser;
    }
    
    public void showToast(String message)
    {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT)
                .show();
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.custom_signin_button:
            {
                String email = mCustomLoginEmail.getText().toString();
                String password = mCustomLoginPassword.getText().toString();
                
                searchUser(email, password);
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                
                if (mEmailAddress != null)
                {
                    mSmartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                    mSmartLogin.login(mSmartLoginConfig);
                } else
                {
                    if (!email.isEmpty() && !password.isEmpty())
                        showToast(Constants.WRONG_EMAIL_OR_PASSWORD_TOAST);
                }
            }
            break;
            case R.id.custom_signup_button:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                
                @SuppressLint("InflateParams")
                View mView = getLayoutInflater().inflate(
                        R.layout.dialog_user_signup,
                        null);
                
                final EditText mEmail = mView.findViewById(R.id.et_email);
                final EditText mPassword = mView.findViewById(R.id.et_password);
                final EditText mConfirmPassword = mView.findViewById(R.id.et_confirm_password);
                Button mAddButton = mView.findViewById(R.id.btn_add);
                Button mCancelButton = mView.findViewById(R.id.btn_cancel);
                
                mAddButton.setOnClickListener(v1 ->
                {
                    String email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    String confirmedPassword = mConfirmPassword.getText().toString();
                    
                    routePostUserCreationData(email, password, confirmedPassword);
                    
                    mAlertDialog.dismiss();
                    showToast(Constants.USER_SIGNED_UP_TOAST);
                });
                
                mCancelButton.setOnClickListener(v2 ->
                {
                    mAlertDialog.dismiss();
                });
                
                mBuilder.setView(mView);
                mAlertDialog = mBuilder.create();
                mAlertDialog.show();
                
                if (!mAlertDialog.isShowing())
                {
                    hideKeyboard();
                    mSmartLogin = SmartLoginFactory.build(LoginType.CustomSignup);
                    mSmartLogin.signup(mSmartLoginConfig);
                }
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
                        mEmailAddress = null;
                    }
                    
                    if (mSmartLogin.logout(getApplicationContext()))
                    {
                        updateUi();
                        showToast(Constants.USER_LOGGED_OUT_TOAST);
                    }
                }
                //navigateToHome();
                break;
        }
    }
    
    public void createUser(UserDTO user)
    {
        Disposable disposable = Observable
                .create((ObservableOnSubscribe<UserDTO>) emitter ->
                {
                    if (!mUsersService.getAllUsers().contains(user))
                        mUsersService.createUser(user);
                    else
                        return;
                    
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doOnError(e -> showToast(e.getMessage()))
                .doOnComplete(() -> onClick(mCustomLoginButton))
                .subscribe();
    }
    
    public void routePostUserCreationData(String email, String password,
                                          String confirmedPassword)
    {
        if (!password.equals(confirmedPassword))
        {
            showToast(Constants.PASSWORDS_NO_MATCH_TOAST);
            return;
        }
        
        if (email.isEmpty() || password.isEmpty())
        {
            showToast(Constants.NOT_ALL_FIELDS_FILLED_TOAST);
            return;
        }
        
        createUser(new UserDTO(email, BCrypt.hashpw(password, BCrypt.gensalt())));
    }
    
    public void searchUser(String email, String password)
    {
        if (email.isEmpty() || password.isEmpty())
        {
            showToast(Constants.NOT_ALL_FIELDS_FILLED_TOAST);
            return;
        }
        
        Disposable disposable = Observable
                .create((ObservableOnSubscribe<UserDTO>) emitter ->
                {
                    UserDTO fakeUser = new UserDTO();
                    fakeUser.setEmail(email);
                    
                    mUsersService.getAllUsers()
                            .forEach(user ->
                            {
                                if (user.equals(fakeUser))
                                    if (BCrypt.checkpw(password, user.getPassword()))
                                    {
                                        mEmailAddress = email;
                                        return;
                                    }
                            });
                    
                    emitter.onComplete();
                })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe();
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
            mCustomLoginEmail.setText(Constants.EMPTY_STRING, TextView.BufferType.NORMAL);
            mCustomLoginPassword.setText(Constants.EMPTY_STRING, TextView.BufferType.NORMAL);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        mSmartLogin.onActivityResult(requestCode, resultCode, data, mSmartLoginConfig);
    }

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
