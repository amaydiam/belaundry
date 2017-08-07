package com.akabetech.belaundryondemand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliAuthenticationAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.Login;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasliTemp;
import com.akabetech.belaundryondemand.retrofit.model.request.LoginRequest;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.etUsernameLogin)
    EditText username;
    @BindView(R.id.etPasswordLogin)
    EditText password;
    @BindView(R.id.pgLoading)
    ProgressBar loading;
    @BindView(R.id.btnLogin)
    Button buttonSubmit;
    ProgressDialog pg;
    @BindView(R.id.tvForgotPass)
    TextView tvForgotPass;
    SingleAuth singleAuth;

    private Callback<Login> loginCallback(final String decryptPassword) {
        return new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                dismissLoading();
                if (response.code() == 200) {
                    UserBeasli data = response.body().getBody();
                    if(data!=null)
                    {
                        ((AuthenticateApplication) getApplication()).getAuth().doLogin(data.getEmail(), decryptPassword);
                        ((AuthenticateApplication) getApplication()).getAuth().setUserAuth(data);
                        startActivity(new Intent(LoginActivity.this, MainMenu.class));
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, response.body().getStatus(), Toast.LENGTH_LONG).show();
                        dismissLoading();
                    }


                }
                else if(response.code()==302)
                {
                    UserBeasliTemp data=new UserBeasliTemp();
                    data.setEmail(username.getText().toString());
                    data.setPassword(password.getText().toString());
                    VerificationCode.setInstance(data);
                    startActivity(new Intent(LoginActivity.this,VerificationCode.class));
                }
                else {

                    try {
                        Log.d("AVAGA", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                dismissLoading();
                Toast.makeText(LoginActivity.this, "belum terdaftar", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        singleAuth = new SingleAuth();
        singleAuth.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        singleAuth = null;

    }

    public AuthenticateApplication getAuthenticateApplication(){
        return  ((AuthenticateApplication)getApplication());
    }

    private void showLoading() {
        buttonSubmit.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    private void dismissLoading() {
        buttonSubmit.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
    @OnClick(R.id.tvForgotPass)
    public void forget(View v)
    {
        startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
    }
    @OnClick(R.id.btnLogin)
    public void doLogin(View v) {
        showLoading();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        AdapterComponent
                .getAdapter(BeAsliAuthenticationAdapter.class)
                .login(loginRequest)
                .enqueue(loginCallback(loginRequest.getPassword()));
    }

    @OnClick(R.id.tvRegisterBtn)
    public void doRegisterPage(View v) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }


    private class SingleAuth extends Thread{
        @Override
        public void run() {
            super.run();
            String auth  = getAuthenticateApplication().getAuth().getBasic();
            if(auth!=null){
                try {
                    loading(true);
                    AdapterComponent
                            .getAdapter(BeAsliAuthenticationAdapter.class)
                            .login(new LoginRequest(auth.split(":")[0],auth.split(":")[1]))
                            .enqueue(loginCallback(auth.split(":")[1]));
                } catch (Exception e) {
                    loading(false);
                    Toast.makeText(LoginActivity.this,"failure to login , try manually",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    this.interrupt();
                }
            }else{
                this.interrupt();
            }
        }

        private void loading(final boolean show){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(show)
                    showLoading();
                    else
                        dismissLoading();
                }
            });
        }
    }
}

