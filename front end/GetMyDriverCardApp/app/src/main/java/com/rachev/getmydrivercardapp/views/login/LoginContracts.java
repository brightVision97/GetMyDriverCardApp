package com.rachev.getmydrivercardapp.views.login;

import android.app.Activity;
import com.rachev.getmydrivercardapp.models.User;

public interface LoginContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
        
        Activity getActivity();
        
        void setUser(User user);
        
        void showProgressBar();
        
        void hideProgressBar();
        
        void performLogin();
        
        void dismissSignupDialog();
        
        void navigateToHome(User user);
    }
    
    interface Presenter
    {
        void subscribe(View view);
        
        void unsubscribe();
        
        void createUser(User user);
        
        void setNavigator(Navigator navigator);
        
        void login(String username, String password);
    }
    
    interface Navigator
    {
        void navigateToHome(User user);
    }
}
