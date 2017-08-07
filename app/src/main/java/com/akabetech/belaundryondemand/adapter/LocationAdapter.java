package com.akabetech.belaundryondemand.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.model.ListLocation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    public List<ListLocation.Prediction> data = null;
    private final GestureDetector gestureDetector;


    private Activity activity;

    private OnLocationItemClickListener OnLocationItemClickListener;


    public LocationAdapter(Activity activity, ArrayList<ListLocation.Prediction> feedbackCustomerList) {
        this.activity = activity;
        this.data = feedbackCustomerList;
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());

    }

    public void setOnLocationItemClickListener(OnLocationItemClickListener onLocationItemClickListener) {
        this.OnLocationItemClickListener = onLocationItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnLocationItemClickListener != null) {
            OnLocationItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }

    public void delete_all() {
        int count = getItemCount();
        if (count > 0) {
            data.clear();
            notifyDataSetChanged();
        }

    }

    public long getItemId(int position) {
        return position;
    }

    public ListLocation.Prediction getItem(int position) {
        if(data.size() == 0) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_location, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.cardLocation.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ListLocation.Prediction Location = data.get(position);
        holder.titleLocation.setText(Location.getStructuredFormatting().getMainText());
        holder.deskripsiLocation.setText(Location.getDescription());
        holder.cardLocation.setTag(position);

    }

    public int getItemCount() {
        return data.size();
    }

    /**
     * Here is the key method to apply the animation
     */

    public void remove(int position) {
        data.remove(data.get(position));
        notifyDataSetChanged();
    }
    public interface OnLocationItemClickListener {

        void onRootClick(View v, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.location_icon)
        ImageView locationIcon;
        @BindView(R.id.title_location)
        TextView titleLocation;
        @BindView(R.id.deskripsi_location)
        TextView deskripsiLocation;
        @BindView(R.id.card_location)
        LinearLayout cardLocation;

        public ViewHolder(View vi) {
            super(vi);
            ButterKnife.bind(this, vi);

        }

    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }


    }


}
