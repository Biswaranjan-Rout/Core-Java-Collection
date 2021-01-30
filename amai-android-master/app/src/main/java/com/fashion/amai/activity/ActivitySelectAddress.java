package com.fashion.amai.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.fashion.amai.BuildConfig;
import com.fashion.amai.R;
import com.fashion.amai.model.AppointmentData;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.FetchAddressIntentService;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class ActivitySelectAddress extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap mMap;
    private Button btnAddAddress;
    private Spinner mySpinner;
    private EditText edt_location_1, city_location, state_location, edt_person_name;

    String edt_person_name_text = "", edt_location_1_text = "", edt_location_2_text = "",
            edt_location_3_text = "", edt_location_4_text = "";

    String errorMessage = "";
    String UserLocation;
    private Location location;

    private View address_progressbar;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private boolean mAddressRequested;

    private String mAddressOutput;
    private AddressResultReceiver mResultReceiver;// Receiver registered with this activity to get the response from FetchAddressIntentService.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getSupportActionBar().setTitle("Select Address");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        address_progressbar = findViewById(R.id.address_progressbar);
        edt_location_1 = findViewById(R.id.edt_location_1);
        city_location = findViewById(R.id.city_location);
        state_location = findViewById(R.id.state_location);
        edt_person_name = findViewById(R.id.edt_person_name);

        mySpinner = (Spinner) findViewById(R.id.spinner_gender);
        btnAddAddress = findViewById(R.id.btn_add_address);
        btnAddAddress.setOnClickListener(this);

        mResultReceiver = new AddressResultReceiver(new Handler());

        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getAddress();
        }

    }

    private void getAddress() {
        address_progressbar.setVisibility(View.VISIBLE);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            //   Log.w(TAG, "onSuccess:null");

                            address_progressbar.setVisibility(View.GONE);
                           // Toast.makeText(ActivitySelectAddress.this, location., Toast.LENGTH_SHORT).show();
                         //   Toast.makeText(ActivitySelectAddress.this, location., Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            mAddressRequested = true;
                        }
                        mLastLocation = location;
                        if (!Geocoder.isPresent()) {
                            //showSnackbar(getString(R.string.no_geocoder_available));
                            return;
                        }
                        if (mAddressRequested) {
                            getLocation(location);
                           // startIntentService();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                           Log.w("Location", "getLastLocation:onFailure", e);
                    }
                });
    }

    private void getLocation(Location location){
        this.location = location;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
          //  Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used);
           // Log.e(TAG, errorMessage + ". " + "Latitude = " + location.getLatitude() +", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
              //  Log.e(TAG, errorMessage);
            }
           // deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            String local = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            //Log.i(TAG, getString(R.string.address_found));
            UserLocation = TextUtils.join(System.getProperty("line.separator"), addressFragments);

            address_progressbar.setVisibility(View.GONE);

            //     Toast.makeText(this, UserLocation, Toast.LENGTH_SHORT).show();

            edt_location_1.setText(local);
            state_location.setText(state);
            city_location.setText(city);

        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            //  Log.i(TAG, "Displaying permission rationale to provide additional context.");

            new AlertDialog.Builder(ActivitySelectAddress.this)
                    .setTitle("Address Permission Needed")
                    .setMessage("We need to access your location ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            ActivityCompat.requestPermissions(ActivitySelectAddress.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            //  Log.i(TAG, "Requesting permission");

            ActivityCompat.requestPermissions(ActivitySelectAddress.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        address_progressbar.setVisibility(View.GONE);


        //  Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {

                //    Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getAddress();
            } else {
                // Permission denied.

                new AlertDialog.Builder(ActivitySelectAddress.this)
                        .setTitle("Address Permission Needed")
                        .setMessage(R.string.permission_denied_explanation)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }

    private void updateUIWidgets() {
        if (mAddressRequested) {
            address_progressbar.setVisibility(ProgressBar.VISIBLE);

            // mFetchAddressButton.setEnabled(false);
        } else {
            address_progressbar.setVisibility(ProgressBar.GONE);
            //  mFetchAddressButton.setEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng eros;
        if (location == null){
            eros = new LatLng(28.364131, 76.935354);

        } else {
            eros = new LatLng(location.getLatitude(), location.getLongitude());

        }
        mMap.addMarker(new MarkerOptions().position(eros).title("EROS Corporate Park"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eros));
    }

    private void sendAddress(JsonObject design) {
        retrofit2.Call<AppointmentData> call = merchantApiInterface.addAppointment( design);
        call.enqueue(new retrofit2.Callback<AppointmentData>() {
            @Override
            public void onResponse(retrofit2.Call<AppointmentData> call, retrofit2.Response<AppointmentData> response) {
                //  Log.d("Design Error", retrofit2.Response.error(400))
                try{
                    if(response.code()==200){

                        startActivity(new Intent(ActivitySelectAddress.this, ActivitySchedule.class));


                        Log.d("*****","Here in block 2");


                    }else if(response.code()==201){

                      //  SelectDesignPojo.DesignData selectDesignPojo = response.body().getData();
                        Log.d("*****","Here in block 2");

                     //   activity_details_progressBar.setVisibility(View.GONE);

                    }else if(response.code()==401){
                        //   showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                    //    activity_details_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                  //  activity_details_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AppointmentData> call, Throwable t) {
                t.printStackTrace();
               // activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_add_address:{
                if (!edt_location_1.getText().toString().isEmpty() && !edt_person_name.getText().toString().isEmpty()){

                    JsonObject design = new JsonObject();

                    String nameStart = mySpinner.getSelectedItem().toString();


                    if (!edt_person_name.getText().toString().isEmpty()){
                        edt_person_name_text = edt_person_name.getText().toString().trim();
                    }

                    if (!edt_location_1.getText().toString().isEmpty()){
                        edt_location_1_text = edt_location_1.getText().toString().trim();
                    }

                    if (!city_location.getText().toString().isEmpty()){
                        edt_location_3_text = city_location.getText().toString().trim();
                    }

                    if (!state_location.getText().toString().isEmpty()){
                        edt_location_4_text = state_location.getText().toString().trim();
                    }

                    try {
                        design.addProperty("address", "1");

                    } catch (JsonIOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                   // todo code to send address to network working
                    // sendAddress(design);

                    MySharedPreferences.registerAddress(preferences, String.valueOf(nameStart + " " + edt_person_name_text + "," +
                            edt_location_1_text + " " + edt_location_2_text +
                            " " + edt_location_3_text + " " + edt_location_4_text));
                    Intent intent = new Intent(ActivitySelectAddress.this, ActivitySchedule.class);
                    intent.putExtra(Constants.SEND_ADDRESS, String.valueOf(nameStart + " " + edt_person_name_text + "," +
                            edt_location_1_text + " " + edt_location_2_text +
                            " " + edt_location_3_text + " " + edt_location_4_text));

                    startActivity(intent);

                }
                break;
            }
        }
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                displayAddressOutput();
            }
        }
    }

    private void displayAddressOutput() {
     //   mLocationAddressTextView.setText(mAddressOutput);

        Toast.makeText(this, mAddressOutput , Toast.LENGTH_SHORT).show();
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();

            mAddressRequested = false;
            updateUIWidgets();
        }
    }

}
