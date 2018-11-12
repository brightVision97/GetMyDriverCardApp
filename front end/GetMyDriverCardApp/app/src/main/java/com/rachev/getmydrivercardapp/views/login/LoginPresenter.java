package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import android.content.Context;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class LoginPresenter implements LoginContracts.Presenter
{
    private LoginContracts.View mView;
    private LoginContracts.Navigator mNavigator;
    private final UsersService<User> mUsersService;
    private final SchedulerProvider mSchedulerProvider;
    
    protected LoginPresenter(Context context)
    {
        mUsersService = GetMyDriverCardApplication.getUsersService(context);
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
    @Override
    public void createUser(final User user)
    {
        if (mView != null)
            mView.showProgressBar();
        Disposable disposable = Observable.create((ObservableOnSubscribe<User>) emitter ->
        {
            try
            {
                User userToAdd = mUsersService.create(user);
                
                if (userToAdd == null)
                    throw new IllegalArgumentException("User already exists");
                
                emitter.onNext(userToAdd);
                emitter.onComplete();
            } catch (Exception e)
            {
                emitter.onError(e);
            }
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(() -> mView.hideProgressBar())
                .subscribe(u -> login(u.getUsername(), u.getPassword()),
                        err ->
                        {
                            if (err instanceof IOException)
                                Methods.showCrouton(mView.getActivity(),
                                        Constants.Strings.CONNECTION_TO_SERVER_TIMED_OUT,
                                        Style.ALERT, true);
                            if (mView != null)
                                Methods.showCrouton(mView.getActivity(),
                                        err.getMessage(), Style.ALERT,
                                        true);
                        });
    }
    
    @Override
    public void login(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
        {
            Methods.showCrouton(mView.getActivity(),
                    Constants.Strings.NOT_ALL_FIELDS_FILLED,
                    Style.ALERT, true);
            return;
        }
        
        final String url = Constants.Strings.BASE_SERVER_URL +
                Constants.Strings.USERS_URL_SUFFIX +
                Constants.Strings.USER_ME_SUFFIX;
        
        mView.showProgressBar();
        Disposable disposable = Observable.create((ObservableOnSubscribe<User>) emitter ->
        {
            
            User currentUser = mUsersService.login(username, password);
            
            if (currentUser == null)
                throw new IllegalArgumentException(Constants.Strings.INCORRECT_CREDENTIALS);
            
            emitter.onNext(currentUser);
            emitter.onComplete();
            
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(user -> mView.navigateToHome(user),
                        e ->
                        {
                            if (e instanceof IllegalArgumentException)
                                Methods.showCrouton(mView.getActivity(),
                                        Constants.Strings.INCORRECT_CREDENTIALS,
                                        Style.ALERT, true);
                            else if (e instanceof IOException)
                                Methods.showCrouton(mView.getActivity(),
                                        Constants.Strings.CONNECTION_TO_SERVER_TIMED_OUT,
                                        Style.ALERT, true);
                        });
    }
}
