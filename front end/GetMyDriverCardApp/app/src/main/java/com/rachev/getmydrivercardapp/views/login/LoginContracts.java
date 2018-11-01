package com.rachev.getmydrivercardapp.views.login;

import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.users.SmartUser;

public interface LoginContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
        
        void showCrouton(String message, Style style, boolean importance);
        
        void performLogin();
        
        void performLogout();
        
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
