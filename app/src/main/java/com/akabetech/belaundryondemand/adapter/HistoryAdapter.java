package com.akabetech.belaundryondemand.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.adapter.viewholder.HistoryViewHolder;
import com.akabetech.belaundryondemand.model.TransactionHistory;
import com.akabetech.belaundryondemand.retrofit.model.beans.Transaction;

import java.util.List;

/**
 * Created by akbar.pambudi on 8/28/2016.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    HistoryViewHolder viewHolder ;
    List<TransactionHistory> transactionHistories;
    List<Transaction> transactions;
    private AdapterView.OnItemClickListener onItemClickListener;
    public HistoryAdapter(List<TransactionHistory> transactionHistories, List<Transaction> transactions) {
        this.transactionHistories = transactionHistories;
        this.transactions = transactions;
    }


    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_content,parent,false);
        viewHolder = new HistoryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        TransactionHistory currentHist =transactionHistories.get(position);
        Transaction currentTrans = transactions.get(position);
        holder.bind(currentHist, currentTrans);
        if(onItemClickListener!=null) holder.setOnItemClickListener(position,onItemClickListener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return transactionHistories.size();
    }


}
