package com.rachev.getmydrivercardapp.views.cardrequest.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.models.BaseRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ItemViewHolder>
{
    private List<BaseRequest> mRequests;
    private OnItemClickListener mOnRequestClickListener;
    
    public RequestsAdapter()
    {
        mRequests = new ArrayList<>();
    }
    
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_list_item, viewGroup, false);
        
        return new ItemViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i)
    {
        itemViewHolder.setOnItemClickListener(mOnRequestClickListener);
        itemViewHolder.bind(mRequests.get(i));
    }
    
    @Override
    public int getItemCount()
    {
        return mRequests.size();
    }
    
    public void setOnRequestClickListener(OnItemClickListener onRequestClickListener)
    {
        this.mOnRequestClickListener = onRequestClickListener;
    }
    
    public void sortByStatus()
    {
        mRequests.sort(Comparator.comparing(BaseRequest::getStatus));
        notifyDataSetChanged();
    }
    
    public void sortByType()
    {
        mRequests.sort(Comparator.comparing(BaseRequest::getType));
        notifyDataSetChanged();
    }
    
    public void sortByDate()
    {
        mRequests.sort(Comparator.comparing(BaseRequest::getRecordCreationDate));
        notifyDataSetChanged();
    }
    
    public boolean isEmpty()
    {
        return mRequests.isEmpty();
    }
    
    public void clear()
    {
        mRequests.clear();
    }
    
    public void addAll(List<BaseRequest> requests)
    {
        mRequests.addAll(requests);
    }
    
    public void add(BaseRequest request)
    {
        mRequests.add(request);
        notifyDataSetChanged();
    }
    
    public void updateStatus(long requestId, String requestStatus)
    {
        mRequests.stream()
                .filter(request -> request.getId() == requestId)
                .findFirst()
                .get()
                .setStatus(requestStatus);
        
        notifyDataSetChanged();
    }
    
    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.request_id)
        TextView mId;
        
        @BindView(R.id.request_type)
        TextView mType;
        
        @BindView(R.id.request_status)
        TextView mStatus;
        
        private BaseRequest mRequest;
        private OnItemClickListener mOnClickListener;
        
        ItemViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        
        void bind(BaseRequest request)
        {
            mRequest = request;
            
            mId.setText(String.format("Request ID: %s", String.valueOf(mRequest.getId())));
            mType.setText(String.format("Type: %s", mRequest.getType()));
            mStatus.setText(String.format("Status: %s", mRequest.getStatus()));
        }
        
        @OnClick
        public void onClick()
        {
            mOnClickListener.onClick(mRequest);
        }
        
        private void setOnItemClickListener(OnItemClickListener onClickListener)
        {
            mOnClickListener = onClickListener;
        }
    }
    
    public interface OnItemClickListener
    {
        void onClick(BaseRequest request);
    }
}
