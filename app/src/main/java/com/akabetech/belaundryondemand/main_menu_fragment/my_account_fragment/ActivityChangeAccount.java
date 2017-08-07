package com.akabetech.belaundryondemand.main_menu_fragment.my_account_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.ProfileFragment;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliUserAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akabetech.belaundryondemand.R.id.currentPasswordField;
import static com.akabetech.belaundryondemand.R.id.retypePassword;

/**
 * Created by mrari on 5/22/2017.
 */

public class ActivityChangeAccount extends AppCompatActivity {
    public static String instance;

    public static String getInstance() {
        return instance;
    }

    public static void setInstance(String instance) {
        ActivityChangeAccount.instance = instance;
    }

    @BindView(R.id.formPassword)
    ConstraintLayout formPassword;

    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.retypePassword)
    EditText retypePassword;
    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.save)
    TextView save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_change_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);
        toolbar_title.setText(getInstance());
        viewLayout(getInstance());
    }

    private void viewLayout(final String instance) {
        final UserBeasli currentData = ((AuthenticateApplication) getApplicationContext()).getAuth().getUserAuth();
        if(!instance.equalsIgnoreCase("change password"))
        {
            formPassword.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String edit = editText.getText().toString();
                    UserBeasli updatedata = new UserBeasli();
                    if(edit.equals(""))
                    {
                        AlertDialog dialog = new AlertDialog.Builder(ActivityChangeAccount.this).setNegativeButton("OK", null).setMessage("All value must be inputted").create();
                        dialog.show();
                    }
                    else
                    {
                        switch (instance.toLowerCase())
                        {
                            case "change name":
                                updatedata.setName(edit);
                                break;
                            case "change email":
                                updatedata.setEmail(edit);
                                break;
                            case "change phone":
                                updatedata.setPhoneNumber(edit);
                                break;
                            case "change address":
                                updatedata.setAddress(edit);
                                break;
                        }
                        AdapterComponent.getAdapter(BeAsliUserAdapter.class).update(currentData.getId(), updatedata)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.code() == 403) {
                                            notifyProfileChangeFailed();
                                        } else {
                                            notifyProfileChangeSucceeded();
                                            AdapterComponent.getAdapter(BeAsliUserAdapter.class).get(currentData.getId()).enqueue(new Callback<UserBeasli>() {
                                                @Override
                                                public void onResponse(Call<UserBeasli> call, Response<UserBeasli> response) {
                                                    ((AuthenticateApplication) getApplicationContext()).getAuth().setUserAuth(response.body());
                                                    finish();
                                                }

                                                @Override
                                                public void onFailure(Call<UserBeasli> call, Throwable t) {

                                                }
                                            });
//
//
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        notifyProfileChangeFailed();
                                    }
                                });

                    }

                }
            });
        }
        else
        {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidForm()) {

                        String auth  = getAuthenticateApplication().getAuth().getBasic();
                        UserBeasli updateData = new UserBeasli();
                        updateData.setPassword(password.getText().toString());
                        updateData.setOldPassword(auth.split(":")[1]);
                        AdapterComponent.getAdapter(BeAsliUserAdapter.class).update(currentData.getId(), updateData)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.code() == 403) {
                                            notifyProfileChangeFailed();
                                        } else {
                                            notifyProfileChangeSucceeded();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        notifyProfileChangeFailed();
                                    }
                                });
                    }
                }
            });
            formPassword.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);

        }

    }
    boolean isValidForm() {
        String newPassword = password.getText().toString();
        String confirmPassword = retypePassword.getText().toString();
        boolean valid = true;
        if (newPassword.equals("") || confirmPassword.equals("")) {
            valid = false;
            AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).setNegativeButton("OK", null).setMessage("All value must be inputted").create();
            dialog.show();
        }

        if (!newPassword.equals(confirmPassword)) {
            valid = false;
            AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).setNegativeButton("OK", null).setMessage("Your new password does not match").create();
            dialog.show();
        }
        return valid;
    }

    private void notifyProfileChangeSucceeded() {
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.ok_change_profile), Toast.LENGTH_LONG)
                .show();
    }

    private void notifyProfileChangeFailed() {
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.error_change_profile), Toast.LENGTH_LONG)
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(item));
    }
    public AuthenticateApplication getAuthenticateApplication(){
        return  ((AuthenticateApplication)getApplication());
    }
}
