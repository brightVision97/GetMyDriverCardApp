package com.rachev.getmydrivercardapp.views.login;

import android.app.Activity;
import studios.codelight.smartloginlibrary.users.SmartUser;

public interface LoginContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
        
        Activity getActivity();
        
        void showProgressBar();
        
        void hideProgressBar();
        
        void performLogin();
        
        void dismissSignupDialog();
        
        void navigateToHome();
    }
    
    interface Presenter
    {
        void subscribe(View view);
        
        void unsubscribe();
        
        void setNavigator(Navigator navigator);
        
        void routePostUserCreationData(String username, String password, String passwordConfirmed);
        
        void prepareAndSendSocialUserDbEntry(SmartUser user);
        
        void fetchSecuredResourcesOnLogin(String username, String password);
    }
    
    interface Navigator
    {
        void navigateToHome();
    }
}
