package com.akabetech.belaundryondemand.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;

/**
 * Created by akbar.pambudi on 8/22/2016.
 */
public class SimpleTwoFieldViewHolder extends RecyclerView.ViewHolder {

    private TextView field1;
    private TextView field2;

    public SimpleTwoFieldViewHolder(View v){
        super(v);
        field1 = ((TextView)v.findViewById(R.id.field_1_simple_twofield));
        field2 = ((TextView)v.findViewById(R.id.field_2_simple_twofield));
    }

    public void bind(String[] value){
        field1.setText(value[0]);
        field2.setText(value[1]);
    }

}
