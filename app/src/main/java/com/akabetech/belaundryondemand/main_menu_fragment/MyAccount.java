package com.akabetech.belaundryondemand.main_menu_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.LoginActivity;
import com.akabetech.belaundryondemand.MainActivity;
import com.akabetech.belaundryondemand.MainMenu;
import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
import com.akabetech.belaundryondemand.main_menu_fragment.history_transaction_fragment.ActivityHistoryDetailItemTransaksi;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.QRCodeFragment;
import com.akabetech.belaundryondemand.main_menu_fragment.home_fragment.TakePictureChooseSourceDialog;
import com.akabetech.belaundryondemand.main_menu_fragment.my_account_fragment.ActivityChangeAccount;
import com.akabetech.belaundryondemand.main_menu_fragment.my_account_fragment.ShowQrCode;
import com.akabetech.belaundryondemand.retrofit.adapter.BeAsliUserAdapter;
import com.akabetech.belaundryondemand.retrofit.model.beans.UserBeasli;
import com.akabetech.belaundryondemand.retrofit.model.request.UpdatePictureRequest;
import com.firebase.client.utilities.Base64;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrari on 5/17/2017.
 */

public class MyAccount extends Fragment implements View.OnClickListener {
    public static MyAccount newInstance() {
        MyAccount fragment = new MyAccount();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.mail)
    TextView mail;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.propic)
    CircleImageView propic;

    @BindView(R.id.cl_changeName)
    ConstraintLayout cl_changeName;

    @BindView(R.id.cl_changeEmail)
    ConstraintLayout cl_changeEmail;

    @BindView(R.id.cl_changePhone)
    ConstraintLayout cl_changePhone;

    @BindView(R.id.cl_changeAddress)
    ConstraintLayout cl_changeAddress;

    @BindView(R.id.cl_changePassword)
    ConstraintLayout cl_changePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_new,container,false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadView();
        setAction();
        loadProfileImage();
    }

    private void setAction() {
        cl_changeName.setOnClickListener(this);
        cl_changeEmail.setOnClickListener(this);
        cl_changePhone.setOnClickListener(this);
        cl_changeAddress.setOnClickListener(this);
        cl_changePassword.setOnClickListener(this);
        propic.setOnClickListener(this);
    }

    private void loadProfileImage() {
        int userId = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId();
        Picasso p = Picasso.with(getContext());
        p.invalidate("http://teikin.com/webservices/public/get-picture/" + userId);
        p.load("http://teikin.com/webservices/public/get-picture/" + userId)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.prof_pic)
                .resize(200, 200)
                .centerCrop()
                .into(propic);
    }

    private void reloadView() {
        UserBeasli userBeasli = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth();
        name.setText(userBeasli.getName());
        mail.setText(userBeasli.getEmail());
        phone.setText(userBeasli.getPhoneNumber());
        address.setText(userBeasli.getAddress());
    }

    @OnClick(R.id.barcodeButton)
    void barcodeClicked() {
        startActivity(new Intent(getContext(), ShowQrCode.class));
        Toast.makeText(getContext(),"jancok",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_signout)
    void logout() {
        ((MainMenu)getContext()).logout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cl_changeName:
                ActivityChangeAccount.setInstance("Change Name");
                startActivity(new Intent(getContext(), ActivityChangeAccount.class));
                break;
            case R.id.cl_changeEmail:
                ActivityChangeAccount.setInstance("Change Email");
                startActivity(new Intent(getContext(), ActivityChangeAccount.class));
                break;
            case R.id.cl_changePhone:
                ActivityChangeAccount.setInstance("Change Phone");
                startActivity(new Intent(getContext(), ActivityChangeAccount.class));
                break;
            case R.id.cl_changeAddress:
                ActivityChangeAccount.setInstance("Change Address");
                startActivity(new Intent(getContext(), ActivityChangeAccount.class));
                break;
            case R.id.cl_changePassword:
                ActivityChangeAccount.setInstance("Change Password");
                startActivity(new Intent(getContext(), ActivityChangeAccount.class));
                break;
            case R.id.propic:
                dispatchTakePictureIntent();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadView();
    }
    public static final int REQUEST_IMAGE_CAPTURE = 200;
    public static final int REQUEST_IMAGE_GALLERY = 100;
    private void dispatchTakePictureIntent() {

        TakePictureChooseSourceDialog dialog = new TakePictureChooseSourceDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getChildFragmentManager(), "");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCameraForCapture();
            } else {
                Toast.makeText(getContext(), R.string.no_perm_camera, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void openCameraForCapture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File profileDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File profileFile = new File(profileDir, "profile_image.jpg");
            Uri profileUri = FileProvider.getUriForFile(getContext(), "com.akabetech.belaundryondemand.fileprovider", profileFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, profileUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            File profileDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File profileFile = new File(profileDir, "profile_image.jpg");
            String realImage = profileFile.getAbsolutePath();
            uploadImage(Uri.parse(realImage));
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            String realImage = getRealPathFromUri(getContext(), selectedImage);
            uploadImage(Uri.parse(realImage));
        }
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private void uploadImage(Uri selectedImage) {
        try {
            String imageData = Base64.encodeFromFile(selectedImage.toString());
            int userId = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId();

            UpdatePictureRequest request = new UpdatePictureRequest();
            request.setImageData(imageData);

            File file = new File(selectedImage.toString());

            RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), fileRequest);

            AdapterComponent.getAdapter(BeAsliUserAdapter.class)
                    .updatePicture(userId, body)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 400) {
                                Toast.makeText(getContext(), R.string.fail_profile_bad_request, Toast.LENGTH_LONG)
                                        .show();
                                return;
                            }
                            Toast.makeText(getContext(), R.string.update_profile_ok, Toast.LENGTH_LONG)
                                    .show();
                            refreshProfilePicture();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getContext(), getString(R.string.update_profile_fail), Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

        } catch (IOException e) {

            Toast.makeText(getContext(), R.string.media_fail, Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    private void refreshProfilePicture() {
        int userId = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId();
        Picasso p = Picasso.with(getContext());
        p.invalidate("http://teikin.com/webservices/public/get-picture/" + userId);
        p.load("http://teikin.com/webservices/public/get-picture/" + userId)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.prof_pic)
                .resize(200, 200)
                .centerCrop()
                .into(propic);
    }

    public void takePicture(boolean takePicture) {
        if (takePicture) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);
            } else {
                openCameraForCapture();
            }
        } else {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
        }
    }
}
