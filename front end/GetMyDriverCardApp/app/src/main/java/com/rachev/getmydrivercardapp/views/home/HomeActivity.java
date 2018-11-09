package com.rachev.getmydrivercardapp.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.views.StartActivity;
import com.rachev.getmydrivercardapp.views.login.LoginActivity;
import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.LoginType;
import studios.codelight.smartloginlibrary.SmartLogin;
import studios.codelight.smartloginlibrary.SmartLoginFactory;
import studios.codelight.smartloginlibrary.UserSessionManager;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

public class HomeActivity extends StartActivity implements View.OnClickListener
{
    @BindView(R.id.grid_layout)
    GridLayout mGridLayout;
    
    @BindView(R.id.my_profile_card_view)
    CardView mMyProfileCardView;
    
    @BindView(R.id.new_request_card_view)
    CardView mNewRequestCardView;
    
    @BindView(R.id.my_requests_card_view)
    CardView mMyRequestsCardView;
    
    @BindView(R.id.logout_card_view)
    CardView mLogoutCardView;
    
    @BindView(R.id.my_profile_button)
    ImageButton mMyProfileButton;
    
    @BindView(R.id.new_request_button)
    ImageButton mNewRequestButton;
    
    @BindView(R.id.my_requests_button)
    ImageButton mMyRequestsButton;
    
    @BindView(R.id.logout_button)
    ImageButton mLogoutButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ButterKnife.bind(this);
        
        mMyProfileCardView.setOnClickListener(this);
        mNewRequestCardView.setOnClickListener(this);
        mMyRequestsCardView.setOnClickListener(this);
        mLogoutCardView.setOnClickListener(this);
        mMyProfileButton.setOnClickListener(this);
        mNewRequestButton.setOnClickListener(this);
        mMyRequestsButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        
        if (getIntent().getBooleanExtra("hasLoggedIn", false))
            Methods.showCrouton(this, Constants.Strings.USER_LOGGED_IN,
                    Style.CONFIRM, false);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_profile_card_view:
                mMyProfileButton.performClick();
                break;
            case R.id.new_request_card_view:
                mNewRequestButton.performClick();
                break;
            case R.id.my_requests_card_view:
                mMyRequestsButton.performClick();
                break;
            case R.id.logout_card_view:
                mLogoutButton.performClick();
                break;
            case R.id.my_profile_button:
                break;
            case R.id.new_request_button:
                break;
            case R.id.my_requests_button:
                break;
            case R.id.logout_button:
                SmartUser currentUser = UserSessionManager.getCurrentUser(this);
                SmartLogin smartLogin = null;
                
                if (currentUser instanceof SmartFacebookUser)
                    smartLogin = SmartLoginFactory.build(LoginType.Facebook);
                else if (currentUser instanceof SmartGoogleUser)
                    smartLogin = SmartLoginFactory.build(LoginType.Google);
                else
                    smartLogin = SmartLoginFactory.build(LoginType.CustomLogin);
                
                smartLogin.logout(getApplicationContext());
                
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isHomeOrigin", true);
                intent.putExtra("hasLoggedOut", true);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
