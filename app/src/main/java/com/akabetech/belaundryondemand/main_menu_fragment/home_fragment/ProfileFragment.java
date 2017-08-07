package com.akabetech.belaundryondemand.main_menu_fragment.home_fragment;


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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akabetech.belaundryondemand.MainActivity;
import com.akabetech.belaundryondemand.R;
import com.akabetech.belaundryondemand.application.api.AuthenticateApplication;
import com.akabetech.belaundryondemand.component.AdapterComponent;
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
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.emailTextView)
    TextView emailTextView;
    @BindView(R.id.passwordTextView)
    TextView passwordTextView;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.addressTextView)
    TextView addressTextView;

    @BindView(R.id.profileImageView)
    CircleImageView profileImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        reloadView();

        return view;
    }


    @OnClick(R.id.takePictureButto)
    void takePicture() {
        dispatchTakePictureIntent();
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

    private void refreshProfilePicture() {
        int userId = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId();
        Picasso p = Picasso.with(getContext());
        p.invalidate("http://teikin.com/webservices/public/get-picture/" + userId);
        p.load("http://teikin.com/webservices/public/get-picture/" + userId)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.prof_pic)
                .resize(200, 200)
                .centerCrop()
                .into(profileImageView);
    }


    @OnClick(R.id.editEmailButton)
    void editEmail() {
        DialogFragment fragment = new ChangeEmailFragment();
        fragment.setTargetFragment(this, 0);
        fragment.show(getChildFragmentManager(), "");
    }

    @OnClick(R.id.editProfileButton)
    void editProfile() {
        DialogFragment fragment = new ChangeProfileFragment();
        fragment.setTargetFragment(this, 0);
        fragment.show(getChildFragmentManager(), "");
    }

    @OnClick(R.id.barcodeButton)
    void barcodeClicked() {
        ((MainActivity) getActivity()).gotoFragment(new QRCodeFragment(), "QR Code");
        ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.sideItemQRCode);
    }

    void reloadView() {
        UserBeasli userBeasli = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth();
        emailTextView.setText(userBeasli.getEmail());
        passwordTextView.setText("********");
        nameTextView.setText(userBeasli.getName());
        phoneTextView.setText(userBeasli.getPhoneNumber());
        addressTextView.setText(userBeasli.getAddress());
        refreshProfilePicture();
    }

    public static class ChangeEmailFragment extends DialogFragment {

        @BindView(R.id.currentPasswordField)
        EditText currentPasswordField;
        @BindView(R.id.newPasswordField)
        EditText newPasswordField;
        @BindView(R.id.confirmNewPasswordFIeld)
        EditText confirmNewPasswordField;

        UserBeasli userBeasli;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_email_change, container, false);
            ButterKnife.bind(this, view);
            userBeasli = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth();
            return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        @OnClick(R.id.saveButton)
        void save() {
            if (isValidForm()) {

                int userId = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId();
                UserBeasli updateData = new UserBeasli();
                updateData.setPassword(newPasswordField.getText().toString());
                updateData.setOldPassword(currentPasswordField.getText().toString());
                AdapterComponent.getAdapter(BeAsliUserAdapter.class).update(userId, updateData)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 403) {
                                    notifyProfileChangeFailed();
                                } else {
                                    notifyProfileChangeSucceeded();
                                    ((ProfileFragment) getTargetFragment()).reloadView();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                notifyProfileChangeFailed();
                            }
                        });
            }
        }

        private void notifyProfileChangeSucceeded() {
            dismiss();
            Toast.makeText(getContext(), getContext().getString(R.string.ok_change_profile), Toast.LENGTH_LONG)
                    .show();
        }

        private void notifyProfileChangeFailed() {
            dismiss();
            Toast.makeText(getContext(), getContext().getString(R.string.error_change_profile), Toast.LENGTH_LONG)
                    .show();
        }

        @OnClick(R.id.cancelButton)
        void cancel() {
            dismiss();
        }

        boolean isValidForm() {
            String currentPassword = currentPasswordField.getText().toString();
            String newPassword = newPasswordField.getText().toString();
            String confirmPassword = confirmNewPasswordField.getText().toString();
            boolean valid = true;
            if (currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
                valid = false;
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setNegativeButton("OK", null).setMessage("All value must be inputted").create();
                dialog.show();
            }

            if (!newPassword.equals(confirmPassword)) {
                valid = false;
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setNegativeButton("OK", null).setMessage("Your new password does not match").create();
                dialog.show();
            }
            return valid;
        }
    }

    public static class ChangeProfileFragment extends DialogFragment {
        @BindView(R.id.nameField)
        EditText nameField;
        @BindView(R.id.addressField)
        EditText addressField;
        @BindView(R.id.phoneField)
        EditText phoneField;

        UserBeasli userBeasli;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile_change, container, false);
            ButterKnife.bind(this, view);
            userBeasli = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth();
            nameField.setText(userBeasli.getName());
            addressField.setText(userBeasli.getAddress());
            phoneField.setText(userBeasli.getPhoneNumber());
            return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @OnClick(R.id.saveButton)
        void save() {
            userBeasli.setName(nameField.getText().toString());
            userBeasli.setAddress(addressField.getText().toString());
            userBeasli.setPhoneNumber(phoneField.getText().toString());

            int userId = ((AuthenticateApplication) getActivity().getApplication()).getAuth().getUserAuth().getId();
            UserBeasli updateData = new UserBeasli();
            updateData.setName(nameField.getText().toString());
            updateData.setName(addressField.getText().toString());
            updateData.setName(phoneField.getText().toString());
            AdapterComponent.getAdapter(BeAsliUserAdapter.class).update(userId, updateData)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dismiss();
                            Toast.makeText(getContext(), getContext().getString(R.string.ok_change_profile), Toast.LENGTH_LONG)
                                    .show();
                            ((ProfileFragment) getTargetFragment()).reloadView();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            dismiss();
                            Toast.makeText(getContext(), getContext().getString(R.string.error_change_profile), Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

        }

        @OnClick(R.id.cancelButton)
        void cancel() {
            dismiss();
        }


        boolean isValidForm() {
            String name = nameField.getText().toString();
            String address = addressField.getText().toString();
            String phone = phoneField.getText().toString();
            boolean valid = true;
            if (name.equals("") || address.equals("") || phone.equals("")) {
                valid = false;
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setNegativeButton("OK", null).setMessage("All value must be inputted").create();
                dialog.show();
            }

            return valid;
        }
    }
}
