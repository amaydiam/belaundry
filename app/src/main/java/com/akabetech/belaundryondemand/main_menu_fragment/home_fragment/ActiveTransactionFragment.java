package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.MainActivity;
import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.HistoryAdapter;
import com.akabetech.belaundryondemand.adapter.viewholder.HistoryViewHolder;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.component.DialogComponent;
import com.akabetech.belaundryondemand.core.comparator.StoreComparator;
import com.akabetech.belaundryondemand.model.TransactionHistory;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliReviewAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliStoreAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliTransactionAdapter;
import com.akabetech.belaundryondemand.retrofit.adapter.TransactionHistoryAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Review;
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
public class ActiveTransactionFragment extends Fragment implements ListView.OnItemClickListener {
    @BindView(R.id.activeRecycleView)
    RecyclerView activeRecyclerView;
    @BindView(R.id.loading_view_active_trans)
    ProgressBar loading;
    @BindView(R.id.tv_no_active_found)
    TextView tvNoDataFound;
    AlertDialog alertDialog;
    List<TransactionHistory> transactionHistories;
    List<Transaction> activeTransaction;

    public ActiveTransactionFragment() {
        // Required empty public constructor
    }


    private void showLoading() {
        loading.setVisibility(View.VISIBLE);
        activeRecyclerView.setVisibility(View.GONE);
    }

    private void dismissLoading() {
        loading.setVisibility(View.GONE);
        activeRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_active_transaction, container, false);
        ButterKnife.bind(this,v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNoDataFound.setVisibility(View.GONE);
        showLoading();
        provideActiveList();

    }

    public void provideActiveList(){
        AdapterComponent.getAdapter(TransactionHistoryAdapter.class)
                .getActiveTransactions(getUser().getId()).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if(response.code() == 200){
                    transactionHistories = new ArrayList<TransactionHistory>();
                    activeTransaction = new ArrayList<Transaction>();
                    final List<Transaction> transactions =response.body();
                    AdapterComponent.getAdapter(BeAsliStoreAdapter.class).getAll().enqueue(new Callback<List<Store>>() {
                        @Override
                        public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                            if(response.code() == 200){

                                Store[] stores = response.body().toArray(new Store[response.body().size()]);

                                for (Transaction trasaction : transactions){
                                    Store storeFind = new Store();
                                    storeFind.setCode(trasaction.getStoreCode());
                                    int index = Arrays.binarySearch(stores,storeFind,new StoreComparator());
                                    if(index>-1){
                                        TransactionHistory transactionHistory = new TransactionHistory(stores[index],
                                                Integer.parseInt(trasaction.getLaundryStatusCode()),
                                                Integer.valueOf(trasaction.getTransaksiTotalPrice()),
                                                trasaction.getDateTransaksi());
                                        transactionHistories.add(transactionHistory);
                                        activeTransaction.add(trasaction);
                                    }
                                }
                                dismissLoading();
                                if(transactionHistories.size() == 0) {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                    activeRecyclerView.setVisibility(View.GONE);
                                }else {
                                    HistoryAdapter activeTransAdapter = new HistoryAdapter(transactionHistories, transactions);
                                    activeTransAdapter.setOnItemClickListener(ActiveTransactionFragment.this);
                                    activeRecyclerView.setAdapter(activeTransAdapter);
                                    activeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        TransactionHistory transaction = transactionHistories.get(position);
        if (transaction.getStatus() != 4) {

            new AlertDialog.Builder(getActivity()).setMessage("Now this transaction is on "
                    + HistoryViewHolder.statusFromStatusCode(transaction.getStatus()) + " status\nYou can confirm this transaction if it is Done").create().show();
            return;
        }

        alertDialog = DialogComponent.getInstance().createDialog(getActivity(), "caution", "are you sure ,done this transaction ?", "yes", "no", new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                alertDialog.dismiss();

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Transaction trasaction = activeTransaction.get(position);

                final Review review = new Review();
                review.setIdUserBeasli("" + getUser().getId());
                review.setStoreCode(trasaction.getStoreCode());

                alertDialog.dismiss();

                alertDialog = DialogComponent.getInstance().createReviewDialog(getActivity(), new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        review.setReview(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (DialogComponent.getInstance().starRating == -1) {
                            Toast.makeText(getActivity(), "Mohon memilih rating", Toast.LENGTH_LONG).show();
                        } else {
                            trasaction.setIsConfirm("1");

                            review.setRating(DialogComponent.getInstance().starRating + "");
                            AdapterComponent.getAdapter(BeAsliTransactionAdapter.class).update(trasaction, trasaction.getCodeTransaksi()).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });

                            AdapterComponent.getAdapter(BeAsliReviewAdapter.class).add(review).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.i("INFO", review.getRating() + review.getReview() + review.getStoreCode() + response.message());
                                    ((MainActivity)getActivity()).gotoFragment(new TransactionHistoriesFragment(),"Transaction History");
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                        alertDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        alertDialog.show();
    }

}
