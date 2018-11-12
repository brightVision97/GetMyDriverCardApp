package com.rachev.getmydrivercardapp.views.cardrequest.lists;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.models.Role;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.utils.Methods;
import com.rachev.getmydrivercardapp.utils.enums.RequestStatus;
import com.rachev.getmydrivercardapp.views.cardrequest.preview.RequestPreviewActivity;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.util.List;

public class RequestsListsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        RequestsAdapter.OnItemClickListener, RequestsListsContracts.View
{
    RequestsListsContracts.Presenter mPresenter;
    private RequestsAdapter mCustomAdapter;
    private BroadcastReceiver mReceiver;
    private User mCurrentUser;
    
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @BindView(R.id.no_requests_text)
    TextView mEmptyListTextView;
    
    @BindView(R.id.spinner)
    Spinner mDropDownSpinner;
    
    @BindView(R.id.spinner_layout)
    RelativeLayout mSpinnerLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);
        setTitle("My requests");
        
        ButterKnife.bind(this);
        
        setPresenter(new RequestsListsPresenter(this));
        mCustomAdapter = new RequestsAdapter();
        mCustomAdapter.setOnRequestClickListener(this);
        mRecyclerView.setAdapter(mCustomAdapter);
        LinearLayoutManager mContactsViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mContactsViewLayoutManager);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sort_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropDownSpinner.setAdapter(adapter);
        mDropDownSpinner.setOnItemSelectedListener(this);
        
        mCurrentUser = (User) getIntent().getSerializableExtra("user");
        
        if (!mCurrentUser.getRoles().contains(new Role(1, "ROLE_ADMIN")))
            FirebaseMessaging.getInstance().subscribeToTopic(mCurrentUser.getUsername());
        
        if (mCurrentUser.getRoles().size() == 2)
            setTitle("All requests");
        
        mReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                try
                {
                    int requestId = intent.getIntExtra("request_id", 0);
                    RequestStatus requestStatus =
                            (RequestStatus) intent.getSerializableExtra("status");
                    mCustomAdapter.updateStatus(requestId, requestStatus.toString());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        
        LocalBroadcastManager.getInstance(this).registerReceiver((mReceiver),
                new IntentFilter("REQUEST_ACCEPT"));
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        mPresenter.subscribe(this);
        
        if (mCustomAdapter.getItemCount() > 0)
            return;
        
        if (mCurrentUser.getRoles().size() == 2)
            mPresenter.loadAllRequests();
        else
            mPresenter.loadUserSpecificRequests(mCurrentUser.getId());
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        
        mPresenter.unsubscribe();
    }
    
    @Override
    public void setPresenter(RequestsListsContracts.Presenter presenter)
    {
        mPresenter = presenter;
    }
    
    @Override
    public Activity getActivity()
    {
        return this;
    }
    
    @Override
    public void showProgressBar()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void hideProgressBar()
    {
        mProgressBar.setVisibility(View.GONE);
    }
    
    @Override
    public void showRequests(List<BaseRequest> requests)
    {
        mCustomAdapter.clear();
        mCustomAdapter.addAll(requests);
        mCustomAdapter.notifyDataSetChanged();
        mSpinnerLayout.setVisibility(View.VISIBLE);
        mEmptyListTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void showRequestPreview(BaseRequest request)
    {
        Intent intent = new Intent(this, RequestPreviewActivity.class);
        intent.putExtra("request", request);
        startActivity(intent);
    }
    
    @Override
    public void showEmptyRequestList()
    {
        mSpinnerLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mEmptyListTextView.setVisibility(View.VISIBLE);
        Methods.showCrouton(this, "Oops",
                Style.INFO, false);
    }
    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (mCustomAdapter.isEmpty())
            return;
        
        String item = parent.getItemAtPosition(position).toString();
        
        switch (item)
        {
            case "sort by status":
                mCustomAdapter.sortByStatus();
                break;
            case "sort by type":
                mCustomAdapter.sortByType();
                break;
            case "sort by date":
                mCustomAdapter.sortByDate();
                break;
        }
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    
    }
    
    @Override
    public void onClick(BaseRequest request)
    {
        RequestPreviewActivity.setRequest(request);
        
        Intent intent = new Intent(this, RequestPreviewActivity.class);
        intent.putExtra("isOriginList", true);
        if (mCurrentUser.getRoles().size() == 2)
            intent.putExtra("admin", true);
        
        startActivityForResult(intent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                int requestId = data.getIntExtra("request_id", 0);
                String requestStatus = data.getStringExtra("request_status");
                mCustomAdapter.updateStatus(requestId, requestStatus);
            }
        }
    }
}
