package com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akabetech.belaundryondemand.MainActivity;
import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.TransactionHistoryAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.TransactionHistoryInProgress;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrari on 5/17/2017.
 */

public class InProgressHistoryTransaction extends Fragment {
    Fragment currentFragment = null;
    public static InProgressHistoryTransaction newInstance() {
        InProgressHistoryTransaction fragment = new InProgressHistoryTransaction();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_historyTransaction)
    RecyclerView rv_historyTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_history_transaction, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromApi();
    }
    public void getDataFromApi()
    {
        AdapterComponent.getAdapter(TransactionHistoryAdapter.class).getInProgressTransaksi(getUser().getId()).enqueue(new Callback<List<TransactionHistoryInProgress>>() {
            @Override
            public void onResponse(Call<List<TransactionHistoryInProgress>> call, Response<List<TransactionHistoryInProgress>> response) {
                List<TransactionHistoryInProgress> data=new ArrayList<TransactionHistoryInProgress>();
                for(TransactionHistoryInProgress obj :response.body())
                {
                    if(obj.getDetailStore()!=null)
                    {
                        data.add(obj);
                    }
                    else {
                        break;
                    }

                }
                historyInProgressAdapter  adapter=new historyInProgressAdapter(data);
                rv_historyTransaction.setAdapter(adapter);
                rv_historyTransaction.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<List<TransactionHistoryInProgress>> call, Throwable t) {

            }
        });
    }
    public UserBeasli getUser(){
        return ((AuthenticateApplication)getActivity().getApplication()).getAuth().getUserAuth();
    }

    public class InProgressHistoryViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_time_transaction,name,phone,address,price,status_transaction;

        public InProgressHistoryViewHolder(View itemView) {
            super(itemView);
            tv_time_transaction=(TextView) itemView.findViewById(R.id.tv_time_transaction);
            name=(TextView) itemView.findViewById(R.id.name);
            phone=(TextView) itemView.findViewById(R.id.phone);
            address=(TextView) itemView.findViewById(R.id.address);
            price=(TextView) itemView.findViewById(R.id.price);
            status_transaction=(TextView) itemView.findViewById(R.id.status_transaction);
        }
        public void bind (TransactionHistoryInProgress historyInProgress)
        {
            tv_time_transaction.setText(historyInProgress.getDateTransaksi());
            name.setText(historyInProgress.getDetailStore().getName());
            phone.setText(historyInProgress.getDateTransaksi());
            address.setText(historyInProgress.getDetailStore().getAddress());
            price.setText(historyInProgress.getTransaksiTotalPrice());
            status_transaction.setText(statusFromStatusCode(Integer.valueOf(historyInProgress.getLaundryStatusCode())));
        }
        public String statusFromStatusCode(int statusCode){
            switch (statusCode){
                case 1 :
                    return "Washing";
                case 2:
                    return "Ironing";
                case 3:
                    return "Pickup";
                case 4:
                    return "Done";
            }
            return "";
        }
    }

    public class historyInProgressAdapter extends RecyclerView.Adapter<InProgressHistoryViewHolder>
    {

        private final List<TransactionHistoryInProgress> historyInprogressList;
        private InProgressHistoryViewHolder viewHolder;

        public historyInProgressAdapter (List<TransactionHistoryInProgress> historyInProgressList)
        {

            this.historyInprogressList = historyInProgressList;
        }
        @Override
        public InProgressHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_history_transaction,parent,false);
            viewHolder = new InProgressHistoryViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(InProgressHistoryViewHolder holder, int position) {
            final TransactionHistoryInProgress currentBind = historyInprogressList.get(position);
            if(historyInprogressList.size()<1) {
                holder.bind(currentBind);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityHistoryDetailItemTransaksi.setInstance(currentBind);
                        startActivity(new Intent(getContext(), ActivityHistoryDetailItemTransaksi.class));
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return historyInprogressList.size()<1?0:historyInprogressList.size();
        }
    }
}
