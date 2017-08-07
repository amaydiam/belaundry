package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;

import net.glxn.qrgen.android.QRCode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QRCodeFragment extends Fragment {

    @BindView(R.id.imvQrCode)
    ImageView qrcode;

    public QRCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_qrcode, container, false);
        ButterKnife.bind(this,v);
        Bitmap qrcodeBitmap = generateQRCode();
        qrcode.setImageBitmap(qrcodeBitmap);

        return v;
    }

    private Bitmap generateQRCode(){
        UserBeasli myUser = ((AuthenticateApplication)getActivity().getApplication()).getAuth().getUserAuth();
            return QRCode.from(myUser.getId()+"").bitmap();
    }

}
