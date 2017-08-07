package com.akabetech.belaundryondemand.handler;

import android.content.Context;
import android.content.SharedPreferences;

import com.akabetech.belaundryondemand.core.Constants;
import com.akabetech.belaundryondemand.handler.api.IAuthenticationHandler;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

/**
 * Created by akbar.pambudi on 8/4/2016.
 */
public class AuthenticationHandler implements IAuthenticationHandler{

    private Context context;
    private SharedPreferences sharedPreferences;
    private UserBeasli userBeasli;
    public AuthenticationHandler(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.SP_NAME,Context.MODE_PRIVATE);

    }


    @Override
    public void doLogin(String username,String password) {
        final String basic = username+":"+password;
                   try {
                   sharedPreferences.edit().putString(Constants.CREDENTIAL_KEY,basic).apply();
                   } catch (Exception e) {
                       e.printStackTrace();
                   }



    }

    @Override
    public String getBasic() {
        if(sharedPreferences.contains(Constants.CREDENTIAL_KEY)){
            return sharedPreferences.getString(Constants.CREDENTIAL_KEY,null);
        }
        return null;
    }

    @Override
    public UserBeasli getUserAuth() {
        return userBeasli;
    }

    @Override
    public void setUserAuth(UserBeasli userAuth) {
        this.userBeasli = userAuth;
    }

    @Override
    public void doLogout() {
        if(sharedPreferences.contains(Constants.CREDENTIAL_KEY)){
             sharedPreferences.edit().remove(Constants.CREDENTIAL_KEY).apply();
        }
    }
}
