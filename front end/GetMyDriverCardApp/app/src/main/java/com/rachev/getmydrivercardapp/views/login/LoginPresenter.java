package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.UserDTO;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.BCrypt;
import com.rachev.getmydrivercardapp.utils.Constants;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

public class LoginPresenter implements LoginContracts.Presenter
{
    private LoginContracts.View mView;
    private final UsersService mUsersService;
    private final SchedulerProvider mSchedulerProvider;
    
    {
        mUsersService = GetMyDriverCardApplication.getUsersService();
        mSchedulerProvider = GetMyDriverCardApplication.getSchedulerProvider();
    }
    
    protected LoginPresenter()
    {
    }
    
    @Override
    public void subscribe(LoginContracts.View view)
    {
        mView = view;
    }
    
    @Override
    public void unsubscribe()
    {
        mView = null;
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void createUser(UserDTO user)
    {
        Observable.create((ObservableOnSubscribe<UserDTO>) emitter ->
        {
            try
            {
                mUsersService.createUser(user);
                emitter.onNext(user);
                emitter.onComplete();
            } catch (Exception e)
            {
                emitter.onError(e);
            }
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(msg -> mView.showCrouton(Constants.USER_SIGNED_UP_TOAST, Style.CONFIRM, false),
                        err -> mView.showCrouton(err.getMessage(), Style.ALERT, true));
    }
    
    @Override
    public void routePostUserCreationData(String username, String password, String confirmedPassword)
    {
        if (!password.equals(confirmedPassword))
        {
            mView.showCrouton(Constants.PASSWORDS_NO_MATCH_TOAST, Style.ALERT, true);
            return;
        }
        
        if (username.isEmpty() || password.isEmpty())
        {
            mView.showCrouton(Constants.NOT_ALL_FIELDS_FILLED_TOAST, Style.ALERT, true);
            return;
        }
        
        createUser(new UserDTO(username.toLowerCase(), BCrypt.hashpw(password, BCrypt.gensalt())));
    }
    
    @Override
    public boolean isPasswordCorrectIfUserExists(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
        {
            mView.showCrouton(Constants.NOT_ALL_FIELDS_FILLED_TOAST, Style.ALERT, true);
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
    
    @Override
    public void prepareAndSendSocialUserDbEntry(SmartUser user)
    {
        UserDTO userToSend = new UserDTO();
        
        if (user instanceof SmartGoogleUser)
        {
            userToSend.setUsername(user.getEmail());
            userToSend.setGoogleId(user.getUserId());
        } else if (user instanceof SmartFacebookUser)
        {
            String customUsername =
                    (((SmartFacebookUser) user).getProfileName().toLowerCase())
                            + user.getUserId().substring(0, 4)
                            .replace(" ", "");
            userToSend.setUsername(customUsername);
            userToSend.setFacebookId(user.getUserId());
        }
        
        createUser(userToSend);
    }
}
