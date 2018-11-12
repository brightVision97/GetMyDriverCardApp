package com.rachev.getmydrivercardapp.views.cardrequest.lists;

import android.app.Activity;
import com.rachev.getmydrivercardapp.models.BaseRequest;

import java.util.List;

public interface RequestsListsContracts
{
    interface View
    {
        void setPresenter(Presenter presenter);
        
        Activity getActivity();
        
        void showProgressBar();
        
        void hideProgressBar();
    
        void showRequests(List<BaseRequest> requests);
    
        void showRequestPreview(BaseRequest request);
    
        void showEmptyRequestList();
    }
    
    interface Presenter
    {
        void subscribe(View view);
        
        void unsubscribe();
    
        void loadAllRequests();
        
        void loadUserSpecificRequests(int userId);
        
        void presentRequestsToView(List<BaseRequest> requestList);
    
        void selectRequest(BaseRequest request);
    }
}
