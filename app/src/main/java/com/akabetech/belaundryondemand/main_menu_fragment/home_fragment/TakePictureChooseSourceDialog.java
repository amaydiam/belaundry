package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.main_menu_fragment.MyAccount;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andreyyoshuamanik on 9/22/16.
 * To make an app
 */
public class TakePictureChooseSourceDialog extends DialogFragment {

    public interface PictureDialogListener {
        void actionClicked(int id);
    }

    public PictureDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_indicator_detail_content_picture, container, true);
        ButterKnife.bind(this, view);


        return view;
    }

    public static TakePictureChooseSourceDialog createDialog(PictureDialogListener listener) {
        TakePictureChooseSourceDialog dialog = new TakePictureChooseSourceDialog();
        dialog.listener = listener;

        return dialog;
    }


    @OnClick(R.id.gallery_button)
    void galleryClicked() {
        dismiss();
        ((MyAccount)getTargetFragment()).takePicture(false);
    }

    @OnClick(R.id.camera_button)
    void cameraClicked() {
        dismiss();
        ((MyAccount)getTargetFragment()).takePicture(true);
    }
}
