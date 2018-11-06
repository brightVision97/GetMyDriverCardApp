package com.rachev.getmydrivercardapp.views.login;

import android.annotation.SuppressLint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.Methods;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.exceptions.UndeliverableException;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import studios.codelight.smartloginlibrary.users.SmartFacebookUser;
import studios.codelight.smartloginlibrary.users.SmartGoogleUser;
import studios.codelight.smartloginlibrary.users.SmartUser;

import java.util.Collections;

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
                .doFinally(() ->
                {
                    if (mView != null)
                        mView.dismissSignupDialog();
                })
                .subscribe(msg ->
                {
                    if (mView != null)
                        Methods.showCrouton(mView.getActivity(),
                                Constants.Strings.USER_SIGNED_UP,
                                Style.CONFIRM, false);
                }, err ->
                {
                    if (mView != null)
                        Methods.showCrouton(mView.getActivity(),
                                err.getMessage(), Style.ALERT,
                                true);
                });
    }
    
    @Override
    public void routePostUserCreationData(String username, String password, String confirmedPassword)
    {
        if (!password.equals(confirmedPassword))
        {
            Methods.showCrouton(mView.getActivity(),
                    Constants.Strings.PASSWORDS_NOT_MATCHING,
                    Style.ALERT, true);
            return;
        }
        
        if (username.isEmpty() || password.isEmpty())
        {
            Methods.showCrouton(mView.getActivity(),
                    Constants.Strings.NOT_ALL_FIELDS_FILLED,
                    Style.ALERT, true);
            return;
        }
        
        createUser(new User(username, password));
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
    
    @SuppressLint("CheckResult")
    @Override
    public void fetchSecuredResourcesOnLogin(String username, String password)
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
        Observable.create((ObservableOnSubscribe<User>) emitter ->
        {
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            
            HttpComponentsClientHttpRequestFactory factory =
                    new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(Constants.Integers.REST_CONNECT_TIMEOUT);
            factory.setReadTimeout(Constants.Integers.REST_READ_TIMEOUT);
            RestTemplate restTemplate = new RestTemplate(factory);
            
            MappingJackson2HttpMessageConverter messageConverter =
                    new MappingJackson2HttpMessageConverter();
            messageConverter.setObjectMapper(new ObjectMapper().configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false));
            
            restTemplate.getMessageConverters().add(messageConverter);
            
            try
            {
                ResponseEntity<User> responseEntity = restTemplate.exchange(
                        url, HttpMethod.GET, new HttpEntity<>(requestHeaders), User.class);
                
                emitter.onNext(responseEntity.getBody());
                emitter.onComplete();
            } catch (HttpClientErrorException e)
            {
                emitter.onError(e);
            } catch (ResourceAccessException e)
            {
                emitter.onError(e);
            }
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(user -> mView.navigateToHome(),
                        e ->
                        {
                            if (e.getMessage().trim().equals(HttpStatus.UNAUTHORIZED.toString())
                                    || e.getMessage().trim().equals(HttpStatus.FORBIDDEN.toString()))
                                Methods.showCrouton(mView.getActivity(),
                                        Constants.Strings.INCORRECT_CREDENTIALS,
                                        Style.ALERT, true);
                            else if (e instanceof ResourceAccessException ||
                                    e instanceof UndeliverableException)
                                Methods.showCrouton(mView.getActivity(),
                                        Constants.Strings.CONNECTION_TO_SERVER_TIMED_OUT,
                                        Style.ALERT, true);
                            else
                            {
                                if (mView != null)
                                    mView.performLogin();
                            }
                        });
    }
}
