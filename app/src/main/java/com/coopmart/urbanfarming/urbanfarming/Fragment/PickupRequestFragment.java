package com.coopmart.urbanfarming.urbanfarming.Fragment;


import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coopmart.urbanfarming.urbanfarming.Activity.HomeActivity;
import com.coopmart.urbanfarming.urbanfarming.Method.Navigator;
import com.coopmart.urbanfarming.urbanfarming.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupRequestFragment extends Fragment {

    public static final String TAG = "Permintaan Pengambilan Produk";

    @BindView(R.id.fpr_pruduct_spinner) Spinner productSpinner;
    @BindView(R.id.fpr_input_qty) EditText amount;
    @BindView(R.id.fpr_output_price) TextView price;
    @BindView(R.id.fpr_pickup_spinner) Spinner pickupSpinner;
    @BindView(R.id.fpr_output_product_image) ImageView productImage;
    @BindView(R.id.fpr_input_address) EditText address;
    @BindView(R.id.fpr_input_additional_detail) EditText pickupDetail;
    @BindView(R.id.fpr_trigger_submit) Button submit;

    public PickupRequestFragment() {
        // Required empty public constructor
    }

    @OnTextChanged(R.id.fpr_input_qty)
    public void setPrice() {
        price.setText("Rp. " + (Integer.parseInt(amount.getText().toString())*250));
    }

    @OnClick(R.id.fpr_trigger_upload_image)
    public void uploadImage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Navigator.CODE_REQUEST_EXTERNAL_STORAGE);
                return;
            }
        }
        pickImageFromGallery();
    }

    @OnClick(R.id.fpr_input_address)
    public void editAddress() {
        if(address.getText() == null || address.getText().toString().equals("")) {
            pickLocation();
        }
    }

    @OnClick(R.id.fpr_trigger_pick_location)
    public void pickLocation() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Navigator.CODE_REQUEST_LOCATION);
                return;
            }
        }
        pickLocationFromGoogleMpas();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Navigator.CODE_REQUEST_EXTERNAL_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            }
        } else if(requestCode == Navigator.CODE_REQUEST_LOCATION) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickLocationFromGoogleMpas();
            }
        }
    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1212);
    }

    private void pickLocationFromGoogleMpas() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(PickupRequestFragment.this.getActivity()), Navigator.FROM_GOOGLE_MAPS_PICK_LOCATION);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Navigator.FROM_GALLERY) {
            if(resultCode == getActivity().RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    Log.d(getClass().toString(), "width: " + image.getWidth() + ", height: " + image.getHeight());
                    productImage.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Fail to retrieve image data", Toast.LENGTH_LONG).show();
                }
            }
        } else if(requestCode == Navigator.FROM_GOOGLE_MAPS_PICK_LOCATION) {
            if(resultCode == getActivity().RESULT_OK) {
                final Place place = PlacePicker.getPlace(getActivity(), data);
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                String attributions = (String) place.getAttributions();
                if (attributions == null) {
                    attributions = "";
                }
                Log.d(getClass().toString(), "Name: " + name + ", Address:" + address + ", Attrib:" + attributions);
                this.address.setText(name + ", " + address);
            }
        }
    }

    @OnClick(R.id.fpr_trigger_submit)
    public void submit() {
        if (!validate()) {
            onSubmitFailed();
            return;
        }

        submit.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(PickupRequestFragment.this.getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.show();

        String product = productSpinner.getSelectedItem().toString();
        String productAmount = amount.getText().toString();
        String session = pickupSpinner.getSelectedItem().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSubmitSuccess or onSubmitFailed
                        // depending on success
                        onSubmitSuccess();
                        // onSubmitFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String product = productSpinner.getSelectedItem().toString();
        String productAmount = amount.getText().toString();
        String session = pickupSpinner.getSelectedItem().toString();
        Bitmap imageBMP = ((BitmapDrawable) productImage.getDrawable()).getBitmap();
        String address = this.address.getText().toString();
        String detail = pickupDetail.getText().toString();

        if (product.isEmpty() || product.equals("-pilih produk-")) {
//            productSpinner.setError("at least 3 characters");
            valid = false;
        } else {
//            productSpinner.setError(null);
        }

        if (productAmount.isEmpty()) {
            this.amount.setError("Masukkan jumlah produk");
            valid = false;
        } else {
            this.amount.setError(null);
        }

        if (session.isEmpty() || session.equals("-pilih sesi-")) {
//            this.pickupSpinner.setError("can not be empty");
            valid = false;
        } else {
//            this.pickupSpinner.setError(null);
        }

        if(imageBMP == null) {
            valid = false;
        } else {

        }

        if (address.isEmpty()) {
            this.address.setError("Pilih alamat anda");
            valid = false;
        } else {
            this.address.setError(null);
        }

        return valid;
    }

    public void onSubmitSuccess() {
        submit.setEnabled(true);

        ((HomeActivity) getActivity()).popBackStack().changeHomeActivityDisplayedFragment(new PickupFragment(), false);
    }

    public void onSubmitFailed() {
        Toast.makeText(getActivity(), "Gagal membuat request pick-up", Toast.LENGTH_LONG).show();

        submit.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_request, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TAG);
        ButterKnife.bind(this, view);

        setSpinner();

        return view;
    }

    public void setSpinner() {
        displayProductNameSpinnerContent();
        displayPickupSessionSpinnerContent();
    }

    private List<String> getProductNameFromServer() {
        List<String> list = new ArrayList<>();
        list.add("-pilih produk-");
        list.add("Kangkung");
        list.add("Cai sim");
        list.add("Wortel");
        list.add("Bawang Merah");
        list.add("Bawang Putih");
        list.add("Kacang Kedelai");
        list.add("Kacang Hijau");
        return list;
    }

    private void displayProductNameSpinnerContent() {
        List<String> productList = getProductNameFromServer();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, productList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(dataAdapter);
    }

    private List<String> getPickupSessionFromServer() {
        List<String> list = new ArrayList<>();
        list.add("-pilih sesi-");
        list.add("Sesi 1 (05.00 - 08.00)");
        list.add("Sesi 2 (13.30 - 15.30)");
        list.add("Sesi 3 (19.00 - 21.00)");
        return list;
    }

    private void displayPickupSessionSpinnerContent() {
        List<String> sessionList = getPickupSessionFromServer();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, sessionList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickupSpinner.setAdapter(dataAdapter);
    }
}
