package com.rachev.getmydrivercardapp.views.cardrequest.preview;

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

public class RequestPreviewPresenter implements RequestPreviewContracts.Presenter
{
    private RequestsService mRequestsService;
    private SchedulerProvider mSchedulerProvider;
    private RequestPreviewContracts.View mView;
    
    public RequestPreviewPresenter(Context context)
    {
        mRequestsService = GetMyDriverCardApplication.getRequestsService(context);
        mSchedulerProvider = GetMyDriverCardApplication.getSchedulerProvider();
    }
    
    @Override
    public void subscribe(RequestPreviewContracts.View view)
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
    public void createRequest(BaseRequest baseRequest)
    {
        mView.showProgressBar();
        Observable.create((ObservableOnSubscribe<BaseRequest>) emitter ->
        {
            BaseRequest requestToPush = mRequestsService.create(baseRequest);
            
            emitter.onNext(requestToPush);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mView::hideProgressBar)
                .subscribe(r ->
                {
                    if (r != null)
                        Methods.showCrouton(mView.getActivity(),
                                "Card request was sent",
                                Style.CONFIRM, false);
                    mView.navigateToHome();
                }, err -> Methods.showCrouton(mView.getActivity(),
                        err.getMessage(), Style.ALERT,
                        true));
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void updateStatus(BaseRequest baseRequest)
    {
        mView.showProgressBar();
        Observable.create((ObservableOnSubscribe<BaseRequest>) emitter ->
        {
            mRequestsService.updateStatus(baseRequest);
            
            emitter.onNext(baseRequest);
            emitter.onComplete();
        })
                .subscribeOn(mSchedulerProvider.background())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(() -> mView.hideProgressBar())
                .subscribe(r -> mView.finalizeRequestReview(r),
                        e -> Methods.showCrouton(mView.getActivity(),
                                e.getMessage(), Style.ALERT,
                                true));
    }
}
