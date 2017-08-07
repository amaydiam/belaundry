package com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.retrofit.model.beans.DetailItemHistoryTransaksi;
import com.akabetech.belaundryondemand.retrofit.model.beans.TransactionHistoryInProgress;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mrari on 5/21/2017.
 */

public class ActivityHistoryDetailItemTransaksi extends AppCompatActivity {
    public static TransactionHistoryInProgress instance;

    public static TransactionHistoryInProgress getInstance() {
        return instance;
    }

    public static void setInstance(TransactionHistoryInProgress instance) {
        ActivityHistoryDetailItemTransaksi.instance = instance;
    }

    @BindView(R.id.dateTransaksi)
    TextView dateTransaksi;

    @BindView(R.id.nameCustomerTransaksi)
    TextView nameCustomerTransaksi;

    @BindView(R.id.addressCustomerTransaksi)
    TextView addressCustomerTransaksi;

    @BindView(R.id.phoneCustomerTransaksi)
    TextView phoneCustomerTransaksi;

    @BindView(R.id.statusTransaksi)
    TextView statusTransaksi;

    @BindView(R.id.storeNameTransaksi)
    TextView storeNameTransaksi;

    @BindView(R.id.addressStoreTransaksi)
    TextView addressStoreTransaksi;

    @BindView(R.id.itemDetailTransaksiHistory)
    RecyclerView itemDetailTransaksiHistory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_history_transaksi);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        toolbar_title.setText("Laundry Detail");
        getDataFromApi();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(item));
    }
    private void getDataFromApi() {
        UserBeasli userBeasli = ((AuthenticateApplication) getApplicationContext()).getAuth().getUserAuth();
        nameCustomerTransaksi.setText(userBeasli.getName());
        addressCustomerTransaksi.setText(userBeasli.getAddress());
        phoneCustomerTransaksi.setText(userBeasli.getPhoneNumber());
        dateTransaksi.setText(ActivityHistoryDetailItemTransaksi.getInstance().getDateTransaksi());
        statusTransaksi.setText(statusFromStatusCode(Integer.valueOf(ActivityHistoryDetailItemTransaksi.getInstance().getLaundryStatusCode())));
        storeNameTransaksi.setText(ActivityHistoryDetailItemTransaksi.getInstance().getDetailStore().getName());
        addressStoreTransaksi.setText(ActivityHistoryDetailItemTransaksi.getInstance().getDetailStore().getAddress());
        HistoryDetailItemAdapter adapter = new HistoryDetailItemAdapter(getInstance().getItem());
        itemDetailTransaksiHistory.setAdapter(adapter);
        itemDetailTransaksiHistory.setLayoutManager(new LinearLayoutManager(this));
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

    public class ActivityHistoryDetailItemViewHolder extends RecyclerView.ViewHolder{
        TextView titleItemTransaksi,itemTransaksi,packageServiceItemTraksi,noteItemTransaksi,feeItemTransaksi;
        public ActivityHistoryDetailItemViewHolder(View itemView) {
            super(itemView);
            titleItemTransaksi =(TextView)itemView.findViewById(R.id.titleItemTransaksi);
            itemTransaksi =(TextView)itemView.findViewById(R.id.itemTransaksi);
            packageServiceItemTraksi =(TextView)itemView.findViewById(R.id.packageServiceItemTraksi);
            noteItemTransaksi =(TextView)itemView.findViewById(R.id.noteItemTransaksi);
            feeItemTransaksi =(TextView)itemView.findViewById(R.id.feeItemTransaksi);
        }
        public void bind(DetailItemHistoryTransaksi item)
        {
            titleItemTransaksi.setText(item.getTitleItemTransaksi());
            itemTransaksi.setText(item.getItemTransaksi());
            packageServiceItemTraksi.setText(item.getPackageServiceItemTraksi());
            noteItemTransaksi.setText(item.getNoteItemTransaksi());
            feeItemTransaksi.setText(item.getFeeItemTransaksi());
        }
    }
    public class HistoryDetailItemAdapter extends RecyclerView.Adapter<ActivityHistoryDetailItemViewHolder>{

        private final List<DetailItemHistoryTransaksi> listItem;

        public HistoryDetailItemAdapter (List<DetailItemHistoryTransaksi> listItem ){
            this.listItem=listItem;
        }
        @Override
        public ActivityHistoryDetailItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_transaksi_history,parent,false);
            ActivityHistoryDetailItemViewHolder viewHolder = new ActivityHistoryDetailItemViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ActivityHistoryDetailItemViewHolder holder, int position) {
            final DetailItemHistoryTransaksi currentBind = listItem.get(position);
            holder.bind(currentBind);
        }

        @Override
        public int getItemCount() {
            return listItem.size();
        }
    }
}
