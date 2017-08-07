package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.HistoryAdapter;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.core.comparator.StoreComparator;
import com.akabetech.belaundryondemand.model.TransactionHistory;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.TransactionHistoryAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.retrofit.model.beans.Transaction;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistoriesFragment extends Fragment {

    @BindView(R.id.historyRecycleView)
    RecyclerView historiesRecycleView;
    @BindView(R.id.loading_view_hist_trans)
    ProgressBar loading;
    @BindView(R.id.tv_no_hist_found)
    TextView tvNoDataFound;
    List<Store> existingStore;

    List<Transaction> transactions;


    public TransactionHistoriesFragment() {
        // Required empty public constructor
    }

    private void showLoading() {
        loading.setVisibility(View.VISIBLE);
        historiesRecycleView.setVisibility(View.GONE);
    }

    private void dismissLoading() {
        loading.setVisibility(View.GONE);
        historiesRecycleView.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_transaction_histories, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNoDataFound.setVisibility(View.GONE);
        showLoading();
        provideTransactionHistoryList();
    }

    public void provideTransactionHistoryList(){
        AdapterComponent.getAdapter(TransactionHistoryAdapter.class)
                        .getTransactionHistories(getUser().getId()).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if(response.code() == 200){
                    final List<TransactionHistory> transactionHistories = new ArrayList<TransactionHistory>();
                     transactions = response.body();
                    AdapterComponent.getAdapter(BeAsliStoreAdapter.class).getAll().enqueue(new Callback<List<Store>>() {
                        @Override
                        public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                            if(response.code() == 200){
                                Store[] stores = response.body().toArray(new Store[response.body().size()]);
                                for (Transaction transaction : transactions){
                                    Store storeFind = new Store();
                                    storeFind.setCode(transaction.getStoreCode());
                                    int index = Arrays.binarySearch(stores,storeFind,new StoreComparator());
                                    if(index>-1){
                                        TransactionHistory transactionHistory =new TransactionHistory(stores[index],Integer.parseInt(transaction.getLaundryStatusCode()),1,transaction.getDateTransaksi());
                                        transactionHistories.add(transactionHistory);
                                    }
                                }
                                dismissLoading();
                                if(transactionHistories.size() == 0) {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                    historiesRecycleView.setVisibility(View.GONE);
                                }else {
                                    historiesRecycleView.setAdapter(new HistoryAdapter(transactionHistories, transactions));
                                    historiesRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Store>> call, Throwable t) {
                            dismissLoading();
                            Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
    }

    public UserBeasli getUser(){
        return ((AuthenticateApplication)getActivity().getApplication()).getAuth().getUserAuth();
    }


}
