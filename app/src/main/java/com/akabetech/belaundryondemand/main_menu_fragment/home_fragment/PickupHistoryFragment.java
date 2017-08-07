package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliPickupHistoryAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Pickup;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickupHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickupHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STORE_CODE = "store_code";


    public PickupHistoryFragment() {
        // Required empty public constructor
    }

    PickupHistoryAdapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PickupHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickupHistoryFragment newInstance(String storeCode) {
        PickupHistoryFragment fragment = new PickupHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STORE_CODE, storeCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PickupHistoryAdapter(new ArrayList<Pickup>(0));
        if (getArguments() != null) {
            loadHistoryData();
        }
    }

    private void loadHistoryData() {
        String storeCodeArg = ((Store) getArguments().getParcelable(StoreDialog.STORE)).getCode();
        String userId = String.valueOf(((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId());
        Log.d("CDEBUG", "Requesting history from " + storeCodeArg + " as " + userId);
        AdapterComponent.getAdapter(BeAsliPickupHistoryAdapter.class)
                .get(storeCodeArg, userId)
                .enqueue(new Callback<List<Pickup>>() {
                    @Override
                    public void onResponse(Call<List<Pickup>> call, Response<List<Pickup>> response) {
                        adapter.source = response.body();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Pickup>> call, Throwable t) {
                        Toast.makeText(getContext(), getString(R.string.pickup_history_fail), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView pickupList = new RecyclerView(getContext());
        pickupList.setBackgroundColor(Color.WHITE);
        pickupList.setHasFixedSize(true);
        pickupList.setLayoutManager(new LinearLayoutManager(getContext()));
        pickupList.setAdapter(adapter);
        return pickupList;
    }

    private class PickupHistoryAdapter extends RecyclerView.Adapter<PickupHistoryViewHolder> {

        private List<Pickup> source;

        private PickupHistoryAdapter(List<Pickup> source) {
            this.source = source;
        }

        @Override
        public PickupHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pickup_history_list_item, parent, false);

            return new PickupHistoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PickupHistoryViewHolder holder, int position) {
            Pickup datum = source.get(position);
            String name = String.valueOf(((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getName());
            holder.dateMark.setText(datum.getPickupDate());
            if(datum.getTimePickup().equals("00:00:00"))
            {
                holder.llTime.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.llTime.setVisibility(View.VISIBLE);
            }
            holder.timeMark.setText(datum.getTimePickup());
            holder.name.setText(name);
            holder.address.setText(datum.getAddress());
            holder.phone.setText(datum.getPhone());
            holder.status.setText(datum.getPickupStatus().equals("0")?"Not Yet":datum.getPickupStatus());
            if(datum.getPickupStatus().equalsIgnoreCase("Cancel")){
                holder.status.setTextColor(getResources().getColor(R.color.warningColor));
            }

            holder.note.setText(datum.getNotePickup());
        }

        @Override
        public int getItemCount() {
            return source.size();
        }
    }

    class PickupHistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_mark)
        TextView dateMark;
        @BindView(R.id.llTime)
        LinearLayout llTime;
        @BindView(R.id.time_mark)
        TextView timeMark;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.note)
        TextView note;
        @BindView(R.id.status)
        TextView status;

        PickupHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
