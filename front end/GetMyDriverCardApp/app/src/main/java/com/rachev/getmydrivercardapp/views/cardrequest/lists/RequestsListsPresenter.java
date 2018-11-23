package com.rachev.getmydrivercardapp.views.cardrequest.lists;

import android.annotation.SuppressLint;
import android.content.Context;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.services.base.RequestsService;
import com.rachev.getmydrivercardapp.utils.Methods;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.util.List;

public class RequestsListsPresenter implements RequestsListsContracts.Presenter
{
    private RequestsListsContracts.View mView;
    private RequestsService mRequestsService;
    private SchedulerProvider mSchedulerProvider;
    
    public RequestsListsPresenter(Context context)
    {
        mRequestsService = GetMyDriverCardApplication.getRequestsService(context);
        mSchedulerProvider = GetMyDriverCardApplication.getSchedulerProvider();
    }
    
    @Override
    public void subscribe(RequestsListsContracts.View view)
    {
        mView = view;
    }
    
    @Override
    public void unsubscribe()
    {
        mView = null;
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void loadAllRequests()
    {
        mView.showProgressBar();
        Observable.create((ObservableOnSubscribe<List<BaseRequest>>) emitter ->
        {
            List<BaseRequest> requests = mRequestsService.getAll();
            
            emitter.onNext(requests);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(this::presentRequestsToView,
                        e -> Methods.showCrouton(mView.getActivity(),
                                e.getMessage(), Style.ALERT,
                                true));
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void loadUserSpecificRequests(int userId)
    {
        mView.showProgressBar();
        Observable.create((ObservableOnSubscribe<List<BaseRequest>>) emitter ->
        {
            List<BaseRequest> requests = mRequestsService.getAllRequestsByUserId(userId);
            
            emitter.onNext(requests);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(this::presentRequestsToView,
                        e -> Methods.showCrouton(mView.getActivity(),
                                e.getMessage(), Style.ALERT,
                                true));
    }
    
    @Override
    public void presentRequestsToView(List<BaseRequest> requestList)
    {
        if (requestList.isEmpty())
            mView.showEmptyRequestList();
        else
            mView.showRequests(requestList);
    }
    
    @Override
    public void selectRequest(BaseRequest request)
    {
        mView.showRequestPreview(request);
    }
}
