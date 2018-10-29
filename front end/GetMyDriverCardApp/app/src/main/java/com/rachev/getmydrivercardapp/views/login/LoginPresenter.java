package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.BCrypt;
import com.rachev.getmydrivercardapp.utils.Constants;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class LoginPresenter implements LoginContracts.Presenter
{
    private LoginContracts.View mView;
    private LoginContracts.Navigator mNavigator;
    private final UsersService mUsersService;
    private final SchedulerProvider mSchedulerProvider;
    
    protected LoginPresenter()
    {
        mUsersService = GetMyDriverCardApplication.getUsersService();
        mSchedulerProvider = GetMyDriverCardApplication.getSchedulerProvider();
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
    
    @Override
    public void setNavigator(LoginContracts.Navigator navigator)
    {
        mNavigator = navigator;
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void createUser(User user)
    {
        Observable.create((ObservableOnSubscribe<User>) emitter ->
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
                .subscribe(
                        msg -> mView.showCrouton(Constants.Strings.USER_SIGNED_UP,
                                Style.CONFIRM, false),
                        err -> mView.showCrouton(err.getMessage(),
                                Style.ALERT, true));
    }
    
    @Override
    public void routePostUserCreationData(String username, String password,
                                          String confirmedPassword)
    {
        if (!password.equals(confirmedPassword))
        {
            mView.showCrouton(Constants.Strings.PASSWORDS_NOT_MATCHING,
                    Style.ALERT, true);
            return;
        }
        
        if (username.isEmpty() || password.isEmpty())
        {
            mView.showCrouton(Constants.Strings.NOT_ALL_FIELDS_FILLED,
                    Style.ALERT, true);
            return;
        }
        
        createUser(new User(username.toLowerCase(), BCrypt.hashpw(password, BCrypt.gensalt())));
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void isPasswordCorrectIfUserExists(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
        {
            mView.showCrouton(Constants.Strings.NOT_ALL_FIELDS_FILLED,
                    Style.ALERT, true);
            return;
        }
        
        Observable.create((ObservableOnSubscribe<User>) emitter ->
        {
            try
            {
                User user = mUsersService.getByUsername(username);
                
                emitter.onNext(user);
                emitter.onComplete();
            } catch (Exception e)
            {
                emitter.onError(e);
            }
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(user ->
                {
                    if (BCrypt.checkpw(password, user.getPassword()))
                    {
                        mView.setProfileNameOnLogin(username);
                        mView.performLoginClick(username);
                        mView.showCrouton(Constants.Strings.USER_LOGGED_IN,
                                Style.CONFIRM, false);
                    } else
                        mView.showCrouton(Constants.Strings.WRONG_USERNAME_OR_PASSWORD,
                                Style.ALERT, false);
                }, e -> mView.showCrouton(Constants.Strings.USER_INCORRECT_CREDENTIALS,
                        Style.ALERT, false));
    }
    
    @Override
    public void prepareAndSendSocialUserDbEntry(SmartUser user)
    {
        User userToSend = new User();
        
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
