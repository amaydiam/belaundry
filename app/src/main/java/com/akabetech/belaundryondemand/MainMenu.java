package com.akabetech.belaundryondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.main_menu_fragment.*;

import butterknife.OnClick;


/**
 * Created by mrari on 5/16/2017.
 */

public class MainMenu extends AppCompatActivity {
    private Toolbar toolbar;
    Fragment currentFragment = null;
    private LinearLayout ll_home;
    private LinearLayout ll_history;
    private LinearLayout ll_promo;
    private LinearLayout ll_myAccount;

    private ImageView icHome,icHistory,icPromo,icMyAccount;
    private TextView tvHome,tvHistory,tvPromo,tvMyAccount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
//        toolbar=(Toolbar)findViewById(R.id.toolbar);
        gotoFragment(Home.newInstance(),"Belaundry");
        ll_home=(LinearLayout) findViewById(R.id.ll_home);
        ll_history=(LinearLayout) findViewById(R.id.ll_history);
        ll_promo=(LinearLayout) findViewById(R.id.ll_promo);
        ll_myAccount=(LinearLayout) findViewById(R.id.ll_myAccount);
        icHome=(ImageView)findViewById(R.id.icHome);
        icHistory=(ImageView)findViewById(R.id.icHistory);
        icPromo=(ImageView)findViewById(R.id.icPromo);
        icMyAccount=(ImageView)findViewById(R.id.icMyAccount);

        tvHome=(TextView)findViewById(R.id.tvHome);
        tvHistory=(TextView)findViewById(R.id.tvHistory);
        tvPromo=(TextView)findViewById(R.id.tvPromo);
        tvMyAccount=(TextView)findViewById(R.id.tvMyAccount);
        tabLayoutAction();

    }

    private void tabLayoutAction() {
        ll_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_DOWN)
                {
                    icHome.setImageResource(R.drawable.ic_home_active);
                    icHistory.setImageResource(R.drawable.ic_history_transaksi_inactive);
                    icPromo.setImageResource(R.drawable.ic_promo_inactive);
                    icMyAccount.setImageResource(R.drawable.ic_my_account_inactive);

                    tvHome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    tvHistory.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvPromo.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvMyAccount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    gotoFragment(Home.newInstance(),"Belaundry");
                }
                return false;
            }
        });
        ll_history.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_DOWN)
                {
                    icHome.setImageResource(R.drawable.ic_home_inactive);
                    icHistory.setImageResource(R.drawable.ic_history_transaksi_active);
                    icPromo.setImageResource(R.drawable.ic_promo_inactive);
                    icMyAccount.setImageResource(R.drawable.ic_my_account_inactive);

                    tvHome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvHistory.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    tvPromo.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvMyAccount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    gotoFragment(HistoryTransaction.newInstance(),"History");
                }
                return false;
            }
        });
        ll_promo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_DOWN)
                {
                    icHome.setImageResource(R.drawable.ic_home_inactive);
                    icHistory.setImageResource(R.drawable.ic_history_transaksi_inactive);
                    icPromo.setImageResource(R.drawable.ic_promo_active);
                    icMyAccount.setImageResource(R.drawable.ic_my_account_inactive);

                    tvHome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvHistory.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvPromo.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    tvMyAccount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    gotoFragment(Promo.newInstance(),"Promo");
                }
                return false;
            }
        });
        ll_myAccount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==event.ACTION_DOWN)
                {
                    icHome.setImageResource(R.drawable.ic_home_inactive);
                    icHistory.setImageResource(R.drawable.ic_history_transaksi_inactive);
                    icPromo.setImageResource(R.drawable.ic_promo_inactive);
                    icMyAccount.setImageResource(R.drawable.ic_my_account_active);

                    tvHome.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvHistory.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvPromo.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.LightGrey));
                    tvMyAccount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    gotoFragment(MyAccount.newInstance(),"My Account");
                }
                return false;
            }
        });
//        getSupportActionBar().hide();
//        getSupportActionBar().hide();
    }

    public void gotoFragment(Fragment fragment, String title){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment, title).commit();
//        TextView titleView = (TextView)toolbar.findViewById(R.id.toolbar_title);
//        titleView.setText(title);
        currentFragment = fragment;
    }
    public void logout() {
        ((AuthenticateApplication) getApplication()).getAuth().doLogout();
        Intent intent = new Intent(MainMenu.this, LoginActivity.class);
        startActivity(intent);
        MainMenu.this.finish();
    }
}
