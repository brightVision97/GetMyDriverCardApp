package com.rachev.getmydrivercardapp.views.login;

import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.users.SmartUser;

public interface LoginContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
        
        void showCrouton(String message, Style style, boolean importance);
        
        void setProfileNameOnLogin(String username);
        
        void performLoginClick(String username);
        
        void navigateToHome();
    }
    
    interface Presenter
    {
        void subscribe(View view);
        
        void unsubscribe();
        
        void setNavigator(Navigator navigator);
        
        void routePostUserCreationData(String username, String password, String passwordConfirmed);
        
        void isPasswordCorrectIfUserExists(String username, String password);
        
        void prepareAndSendSocialUserDbEntry(SmartUser user);
    }
    
    interface Navigator
    {
        void navigateToHome();
    }
}
