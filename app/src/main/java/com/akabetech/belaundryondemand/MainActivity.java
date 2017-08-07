package com.akabetech.belaundryondemand;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.DialogComponent;
import com.akabetech.belaundryondemand.core.Constants;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.ActiveTransactionFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.MapStoreFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.ProfileFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.PromosFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.QRCodeFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.TransactionHistoriesFragment;
import com.akabetech.belaundryondemand.model.SimpleActionSheetItem;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.ui.dialog.StoreDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        ImageView profilePicture;
        AlertDialog takePictureDialog;
        Fragment currentFragment = null;
        @BindView(R.id.toolbar) Toolbar toolbar;
    public NavigationView navigationView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        gotoFragment(MapStoreFragment.newInstance(),"Choose Store");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        navigationView.getMenu().getItem(0).setChecked(true);
//        initDashboardValue(navigationView.getHeaderView(0));
//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                int userId = ((AuthenticateApplication) getApplicationContext()).getAuth().getUserAuth().getId();
//                Picasso p = Picasso.with(getApplicationContext());
//                p.invalidate("http://teikin.com/webservices/public/get-picture/" + userId);
//                p.load("http://teikin.com/webservices/public/get-picture/" + userId)
//                        .networkPolicy(NetworkPolicy.NO_CACHE)
//                        .error(R.drawable.prof_pic)
//                        .resize(200,200)
//                        .centerCrop()
//                        .into(profilePicture);
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//
//            }
//        };
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainMenu.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(item));
    }

    public void gotoFragment(Fragment fragment, String title){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain,fragment, title).commit();
        TextView titleView = (TextView)toolbar.findViewById(R.id.toolbar_title);
        titleView.setText(title);
        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().findFragmentByTag("store dialog") != null) {
                StoreDialog dialog = (StoreDialog) getSupportFragmentManager().findFragmentByTag("store dialog");
                MapStoreFragment fragment = (MapStoreFragment)getSupportFragmentManager().findFragmentByTag("Choose Store");
                fragment.showMarkers();
                dialog.dismiss();
                return;
            }
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sideItemHome) {
            if (!currentFragment.getClass().getName().equals(MapStoreFragment.class.getName())) {
                gotoFragment(MapStoreFragment.newInstance(),"Choose Store");
            }
        } else if (id == R.id.sideItemActiveTrans) {
            gotoFragment(new ActiveTransactionFragment(),"Active Transaction");
        } else if (id == R.id.sideItemHistoryTrans) {
            gotoFragment(new TransactionHistoriesFragment(),"Transaction History");
        } else if (id == R.id.sideItemQRCode) {
            gotoFragment(new QRCodeFragment(),"QRCode");
        } else if (id == R.id.sidePromo) {
            gotoFragment(new PromosFragment(), "");
        }
        navigationView.setCheckedItem(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void takeProfilePicture(View v){
      if(takePictureDialog==null) {
          takePictureDialog = DialogComponent.getInstance().getActionSheetDialog("Choose Dialog", new SimpleActionSheetItem[]{
                  new SimpleActionSheetItem("From Camera", new Intent(MediaStore.ACTION_IMAGE_CAPTURE), true, Constants.CAMERA_FEATURE),
                  new SimpleActionSheetItem("From Gallery", new Intent(Intent.ACTION_PICK,
                          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), true, Constants.GALLERY_FEATURE)
          }, this);
      }
        takePictureDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK)return;
        switch (requestCode){
            case ProfileFragment.REQUEST_IMAGE_CAPTURE:
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                profilePicture.setImageBitmap(imageBitmap);
                takePictureDialog.dismiss();
                break;
            case Constants.GALLERY_FEATURE:
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                profilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                takePictureDialog.dismiss();
                break;
            default:

        }
    }

    private void initDashboardValue(View v){
        UserBeasli userBeasli = ((AuthenticateApplication)getApplication()).getAuth().getUserAuth();
        if(v.findViewById(R.id.etNameSidebar) instanceof TextView)
        ((TextView)v.findViewById(R.id.etNameSidebar)).setText(userBeasli.getName());
        if(v.findViewById(R.id.etLocationSidebar) instanceof TextView)
        ((TextView)v.findViewById(R.id.etLocationSidebar)).setText(userBeasli.getAddress());
        profilePicture = (ImageView)v.findViewById(R.id.imvProfilePicSidebar);
        v.findViewById(R.id.profileButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
//                int size = navigationView.getMenu().size();
//                for (int i = 0; i < size; i++) {
//                    navigationView.getMenu().getItem(i).setChecked(false);
//                }
                uncheckAllMenuItems(navigationView);
                gotoFragment(new ProfileFragment(), "Profile");
            }
        });
    }
    private void uncheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    @OnClick(R.id.logout_button)
    void logout() {
        ((AuthenticateApplication) getApplication()).getAuth().doLogout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}
