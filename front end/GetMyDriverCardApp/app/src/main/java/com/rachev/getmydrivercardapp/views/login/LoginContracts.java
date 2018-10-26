package com.rachev.getmydrivercardapp.views.login;

import de.keyboardsurfer.android.widget.crouton.Style;
import studios.codelight.smartloginlibrary.users.SmartUser;

public interface LoginContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
        
        void showCrouton(String message, Style style, boolean importance);
        
        void navigateToHome();
    }
    
    interface Presenter
    {
        void subscribe(View view);
        
        void unsubscribe();
        
        void routePostUserCreationData(String username, String password, String passwordConfirmed);
        
        boolean isPasswordCorrectIfUserExists(String username, String password);
        
        void prepareAndSendSocialUserDbEntry(SmartUser user);
    }
    
    interface Navigator
    {
        void navigateToHome();
    }
}
