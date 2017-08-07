package com.akabetech.belaundryondemand.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.model.TransactionHistory;
import com.akabetech.belaundryondemand.retrofit.model.beans.Transaction;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

/**
 * Created by akbar.pambudi on 8/28/2016.
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder{
    private TextView storeName;
    private TextView stuffKilogram;
    private TextView storePhone;
    private TextView stuffStatus;
    private TextView date;
    private ImageView logoImv;
    private View v;
    public String numberTocurrency(Double nilai)
    {
//        double x =20000;
        String currencyUI;
        DecimalFormat format = new DecimalFormat("###,###,###,###,###");
        currencyUI = format.format(nilai);
        currencyUI = currencyUI.replace(",", ".");
        return currencyUI;
    }
    public HistoryViewHolder(View v) {
        super(v);
        this.storeName = (TextView)v.findViewById(R.id.store_name_history);
        this.stuffKilogram = (TextView)v.findViewById(R.id.historyStuffTotal);
        this.storePhone = (TextView)v.findViewById(R.id.tvPhoneNumberHistory);
        this.stuffStatus = (TextView)v.findViewById(R.id.historyStatus);
        this.logoImv = (ImageView)v.findViewById(R.id.imv_logo_hist);
        this.v = v;
        this.date =(TextView)v.findViewById(R.id.store_date_history);

    }

    public void bind(TransactionHistory transactionHistory, Transaction transaction){
        this.storeName.setText(transactionHistory.getStore().getName());
        this.storePhone.setText(transactionHistory.getStore().getAddress());
        this.stuffStatus.setText(statusFromStatusCode(transactionHistory.getStatus()));
        String price = numberTocurrency(Double.valueOf(transaction.getTransaksiTotalPrice()));
        this.stuffKilogram.setText("IDR " + price);
        Picasso.with(v.getContext()).load("http://owner.belaundry.co.id/assets/images/logo/"+transactionHistory.getStore().getLogo()).placeholder(R.mipmap.placeholder).into(this.logoImv);
        this.date.setText(transactionHistory.getDateTransaksi());
    }

    public void setOnItemClickListener(final int position, final ListView.OnItemClickListener onItemClickListener) {

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(null,v,position,-1);
            }
        });
    }

    public static String statusFromStatusCode(int statusCode){
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
