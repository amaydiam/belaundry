package com.akabetech.belaundryondemand.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.component.DialogComponent;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.MapStoreFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.OurProfileFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.PickupFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.PromoFragment;
import com.akabetech.belaundryondemand.model.Tab;
import com.akabetech.belaundryondemand.retrofit.model.beans.Store;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akbar.pambudi on 8/16/2016.
 */
public class StoreDialog extends DialogFragment implements TabHost.OnTabChangeListener{
    public static final String STORE = "store";
    FragmentTabHost fragmentTabHost;
    FragmentActivity appCompatActivity;
    private ImageView currentImv=null;
    private String currentTab = null;
    private TextView currentTv = null;
    private Drawable currentDraw;
    private Store store;



    @BindView(R.id.frameTabContent) FrameLayout tabLayout;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.select_store_dialog,container);
        ButterKnife.bind(this, v);

        fragmentTabHost =(FragmentTabHost) v.findViewById(R.id.frameTabHost);
        ((TextView)v.findViewById(R.id.tvStoreTitleDialog)).setText(store.getName());


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentTabHost.setup(getActivity(),getChildFragmentManager(),R.id.frameTabContent);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STORE,store);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("tabOurProfile").setIndicator(provideTab(new Tab("Our Profile",R.drawable.icon_profile_map))),OurProfileFragment.class,bundle);
        if (store.getIsPicked() == 1) {
            fragmentTabHost.addTab(fragmentTabHost.newTabSpec("tabPickup").setIndicator(provideTab(new Tab("Pickup",R.drawable.icon_pickup_map))),PickupFragment.class,bundle);
        }
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("tabGoto").setIndicator(provideTab(new Tab("Go to",R.drawable.icon_goto_map))), Fragment.class,null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("tabPromo").setIndicator(provideTab(new Tab("Promo",R.drawable.promo_icon_map))),PromoFragment.class,bundle);
        fragmentTabHost.getTabWidget().getLayoutParams().height = getContext().getResources().getDisplayMetrics().heightPixels/6;
        fragmentTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        p.gravity = Gravity.CENTER;

        getDialog().getWindow().setAttributes(p);

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        tabToggle("tabOurProfile");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        final MapStoreFragment fragment = (MapStoreFragment)getTargetFragment();
        fragment.showMarkers();
        super.onDismiss(dialog);
    }

    private View provideTab(Tab tab){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_content,null);
        ((TextView)v.findViewById(R.id.tvTab)).setText(tab.getText());
        if (tab.getText().equals("Our Profile")) {
            ((TextView)v.findViewById(R.id.tvTab)).setTextSize(12);
        }
        ((ImageView)v.findViewById(R.id.imageTab)).setImageResource(tab.getImageResource());
        ViewGroup.LayoutParams params = ((ImageView)v.findViewById(R.id.imageTab)).getLayoutParams();
        params.height -= 20;
        return v;
    }
    private int getSupportedColor(@ColorRes int colorRes){

        return ContextCompat.getColor(getContext(),colorRes);
    }

    @Override
    public void onTabChanged(String tabId) {
        tabToggle(tabId);
    }

    private void tabToggle(String tabId){
        int res = -1;

        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        p.gravity = Gravity.CENTER;

        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        switch (tabId){
            case "tabOurProfile":
                res = R.drawable.icon_profile_map_active;
                break;
            case "tabPickup":

                res= R.drawable.icon_pickup_map_active;
                break;
            case "tabGoto":
                getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                p.height = (int) (getResources().getDisplayMetrics().heightPixels/6 + (getResources().getDisplayMetrics().heightPixels * 0.09));
                p.gravity = Gravity.TOP;
                getDialog().getWindow().setAttributes(p);

                final MapStoreFragment fragment = (MapStoreFragment)getTargetFragment();
                fragment.hideMarkers();
                final AlertDialog alert = DialogComponent.getInstance().createDialogMapQuestion(getActivity(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // if user said yes, navigation will be started
                        if (isGoogleMapsInstalled()) {
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + store.getLatitude() + "," + store.getLongitude());
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");

                            startActivity(mapIntent);
                        } else {
                            new AlertDialog.Builder(getActivity()).setMessage("You need to have google maps installed to start turn by turn navigation.").create().show();
                        }

                        StoreDialog.this.getDialog().dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // if user choose not to navigate, dismiss the dialog and show direction from user collection
                        fragment.showDirectionToStore(store);
                    }
                });
                alert.show();

                res = R.drawable.gotoactive1;
                break;
            case "tabPromo":
                res = R.drawable.promo_icon_map_active;
                break;
        }
        if(currentTab!=null) {
            currentImv.setImageDrawable(currentDraw);
            currentTv.setTextColor(getSupportedColor(R.color.textNoActive));
        }
        currentImv = ((ImageView)fragmentTabHost.getCurrentTabView().findViewById(R.id.imageTab));
        currentTab = tabId;
        currentTv = ((TextView) fragmentTabHost.getCurrentTabView().findViewById(R.id.tvTab));
        currentDraw = currentImv.getDrawable();
        currentImv.setImageResource(res);
        currentTv.setTextColor(getSupportedColor(R.color.textActive));

    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
}

