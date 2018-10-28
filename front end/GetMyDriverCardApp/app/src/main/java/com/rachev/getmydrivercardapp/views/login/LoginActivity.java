package com.rachev.getmydrivercardapp.views.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import de.keyboardsurfer.android.widget.crouton.Style;

public class LoginActivity extends AppCompatActivity implements LoginContracts.Navigator
{
    public static final int IDENTIFIER = 160;
    
    private LoginFragment mLoginFragment;
    private LoginContracts.Presenter mPresenter;
    private static long mBackPressedTimes;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mPresenter = new LoginPresenter();
        mLoginFragment = LoginFragment.getInstance();
        mPresenter.setNavigator(this);
        mLoginFragment.setNavigator(this);
        mLoginFragment.setPresenter(mPresenter);
        
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_content, mLoginFragment)
                .commit();
    }
    
    @Override
    public void onBackPressed()
    {
        if (mBackPressedTimes + Constants.Integers.BACK_PRESS_PERIOD > System.currentTimeMillis())
            super.onBackPressed();
        else
        {
            mLoginFragment.showCrouton(Constants.Strings.SECOND_PRESS_NOTIFIER,
                    Style.INFO, false);
            mBackPressedTimes = System.currentTimeMillis();
        }
    }
    
    @Override
    public void navigateToHome()
    {
    
    }
}
