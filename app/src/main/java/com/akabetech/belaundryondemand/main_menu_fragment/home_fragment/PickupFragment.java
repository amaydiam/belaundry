package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akabetech.belaundryondemand.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PickupFragment extends Fragment {
    @BindView(R.id.pickupTabHost)
    FragmentTabHost tabHost;
    Unbinder butterKnifeUnbind;

    public PickupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pickup, container, false);
        butterKnifeUnbind = ButterKnife.bind(this, v);
        setupTabs();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupTabs() {
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.pickup_content);
        tabHost.addTab(tabHost.newTabSpec("now").setIndicator("now"), PickupNowFragment.class, this.getArguments());
        tabHost.addTab(tabHost.newTabSpec("later").setIndicator("later"), PickupLaterFragment.class, this.getArguments());
        tabHost.addTab(tabHost.newTabSpec("history").setIndicator("history"), PickupHistoryFragment.class, this.getArguments());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        butterKnifeUnbind.unbind();
    }
}
