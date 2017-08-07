package com.akabetech.belaundryondemand.main_menu_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment.CompleteHistoryTransaction;
import com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment.InProgressHistoryTransaction;
import com.akabetech.belaundryondemand.retrofit.adapter.TransactionHistoryAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Transaction;
import com.akabetech.belaundryondemand.retrofit.model.beans.TransactionHistoryInProgress;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrari on 5/17/2017.
 */

public class HistoryTransaction extends Fragment {
    Fragment currentFragment = null;
    public static HistoryTransaction newInstance() {
        HistoryTransaction fragment = new HistoryTransaction();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.inprogress)
    LinearLayout inprogress;
    @BindView(R.id.complete)
    LinearLayout complete;
    @BindView(R.id.tvComplete)
    TextView tvComplete;
    @BindView(R.id.tvInProgress)
    TextView tvInProgress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_transaction, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gotoFragment(InProgressHistoryTransaction.newInstance(),"Belaundry");
        inprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(InProgressHistoryTransaction.newInstance(),"In Progress");
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(CompleteHistoryTransaction.newInstance(),"Complete");
            }
        });
    }
    public void gotoFragment(Fragment fragment, String title){
        if(title.equalsIgnoreCase("complete"))
        {
            tvComplete.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
            tvInProgress.setTextColor(ContextCompat.getColor(getContext(),R.color.LightGrey));
        }
        if(title.equalsIgnoreCase("in progress"))
        {
            tvComplete.setTextColor(ContextCompat.getColor(getContext(),R.color.LightGrey));
            tvInProgress.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        }
        getChildFragmentManager().beginTransaction().replace(R.id.frameHistoryTransaction,fragment, title).commit();
        currentFragment = fragment;
    }


}
