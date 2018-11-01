package com.rachev.getmydrivercardapp.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.views.login.LoginActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{
    @BindView(R.id.grid_layout)
    GridLayout mGridLayout;
    
    @BindView(R.id.my_profile_card_view)
    CardView mMyProfileCardView;
    
    @BindView(R.id.new_request_card_view)
    CardView mNewRequestCardView;
    
    @BindView(R.id.my_requests_card_view)
    CardView mMyRequestsCardView;
    
    @BindView(R.id.home_logout_card_view)
    CardView mLogoutCardView;
    
    @BindView(R.id.my_profile_button)
    ImageButton mMyProfileButton;
    
    @BindView(R.id.new_request_button)
    ImageButton mNewRequestButton;
    
    @BindView(R.id.my_requests_button)
    ImageButton mMyRequestsButton;
    
    @BindView(R.id.home_logout_button)
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
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_profile_card_view:
                v = mMyProfileButton;
                break;
            case R.id.new_request_card_view:
                v = mNewRequestButton;
                break;
            case R.id.my_requests_card_view:
                v = mMyRequestsButton;
                break;
            case R.id.home_logout_card_view:
                v = mLogoutButton;
            case R.id.my_profile_button:
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("isHomeOrigin", true);
                startActivity(intent);
                break;
            case R.id.new_request_button:
                break;
            case R.id.my_requests_button:
                break;
            case R.id.home_logout_button:
                break;
            default:
                break;
        }
    }
}
