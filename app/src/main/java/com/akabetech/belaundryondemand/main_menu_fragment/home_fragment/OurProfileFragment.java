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

/**
 * A simple {@link Fragment} subclass.
 */
public class OurProfileFragment extends Fragment {

    @BindView(R.id.tabHostAboutUs) FragmentTabHost fragmentTabHost;

    public OurProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_our_profile, container, false);
        ButterKnife.bind(this,v);
        setupTab();
        return v;
    }

    private void setupTab(){
        fragmentTabHost.setup(getActivity(),getChildFragmentManager(),R.id.frameAboutUs);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("aboutUs").setIndicator("About Us"),AboutUsFragment.class,getArguments());
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("priceList").setIndicator("Price list"),PriceListFragment.class,getArguments());
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("review").setIndicator("Review"), ReviewFragment.class, getArguments());
    }

}
