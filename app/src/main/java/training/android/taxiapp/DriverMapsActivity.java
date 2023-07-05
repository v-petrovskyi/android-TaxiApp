package training.android.taxiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

import training.android.taxiapp.databinding.ActivityDriverMapsBinding;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityDriverMapsBinding binding;

    private static final int REQUEST_LOCATION_PERMISSION = 156455;
    private final String TAG = "DriverMapsActivity";
    private static final int CHECK_SETTINGS_CODE = 5654;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;

    private boolean isLocationUpdatesActive;

    private Button settingsButton, singOutButton;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        auth = FirebaseAuth.getInstance();
//        currentUser = auth.getCurrentUser();

//        settingsButton = findViewById(R.id.settingsButton);
//        singOutButton = findViewById(R.id.singOutButton);

//        singOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth.signOut();
//                singOutDriver();
//            }
//        });

        binding = ActivityDriverMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        settingsClient = LocationServices.getSettingsClient(this);
//        buildLocationRequest();
//        buildLocationCallBack();
//        buildLocationSettingsRequest();
//
//        startLocationUpdates();
    }

//    private void singOutDriver() {
//        String driverUserId = currentUser.getUid();
//        DatabaseReference drivers = FirebaseDatabase.getInstance().getReference().child("drivers");
//        GeoFire geoFire = new GeoFire(drivers);
//        geoFire.removeLocation(driverUserId);
//        Intent intent = new Intent(this, ChooseModeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHECK_SETTINGS_CODE) {
//            switch (resultCode) {
//                case Activity.RESULT_OK:
//                    Log.d(TAG, "user has agreed to change location settings");
//                    startLocationUpdates();
//                    break;
//                case Activity.RESULT_CANCELED:
//                    Log.d(TAG, "user has not agreed to change location settings");
//                    isLocationUpdatesActive = false;
//                    updateLocationUi();
//                    break;
//            }
//        }
//    }
//
//    private void buildLocationSettingsRequest() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(locationRequest);
//        locationSettingsRequest = builder.build();
//    }
//
//    private void buildLocationCallBack() {
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                currentLocation = locationResult.getLastLocation();
//
//                updateLocationUi();
//            }
//        };
//    }
//
//    private void updateLocationUi() {
//        if (currentLocation!=null){
//            LatLng driverLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLocation));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
//            mMap.addMarker(new MarkerOptions().position(driverLocation).title("Driver location"));
//
//            String driverUserId = currentUser.getUid();
//            DatabaseReference drivers = FirebaseDatabase.getInstance().getReference().child("drivers");
//            GeoFire geoFire = new GeoFire(drivers);
//            geoFire.setLocation(driverUserId, new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude()));
//        }
//    }
//
//    private void buildLocationRequest() {
//        locationRequest = LocationRequest.create();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(5000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (isLocationUpdatesActive && checkLocationPermission()) {
//            startLocationUpdates();
//        } else if (!checkLocationPermission()) {
//            requestLocationPermission();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopLocationUpdates();
//    }
//
//    private void stopLocationUpdates() {
//        if (!isLocationUpdatesActive){
//            return;
//        }else {
//            fusedLocationClient.removeLocationUpdates(locationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>(){
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    isLocationUpdatesActive = false;
//                }
//            });
//        }
//    }
//
//    private void requestLocationPermission() {
//        boolean shouldProvide = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
//        if (shouldProvide){
//            showSnackBar("location permission is needed for app functionality", "OK", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//                }
//            });
//        } else {
//            ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_LOCATION_PERMISSION){
//            if (grantResults.length == 0){
//                Log.d(TAG, "onRequestPermissionsResult: Request was canceled");
//            } else if (grantResults[0]== PackageManager.PERMISSION_GRANTED) {
//                startLocationUpdates();
//            } else {
//                showSnackBar("Turn on location on settings", "Settings", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", DriverMapsActivity.this.getPackageName(), null);
//                        intent.setData(uri);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                });
//            }
//        }
//    }
//
//    private void showSnackBar(final String mainText, final String action, View.OnClickListener listener) {
//        Snackbar.make(findViewById(android.R.id.content), mainText, Snackbar.LENGTH_INDEFINITE).setAction(action, listener).show();
//    }
//
//    private boolean checkLocationPermission() {
//        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void startLocationUpdates() {
//        isLocationUpdatesActive = true;
//
//        settingsClient.checkLocationSettings(locationSettingsRequest).addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
//            @Override
//            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                if (ActivityCompat.checkSelfPermission(DriverMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DriverMapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//                updateLocationUi();
//            }
//        }).addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, e.getMessage());
//                int statusCode = ((ApiException) e).getStatusCode();
//                switch (statusCode) {
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        try {
//                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                            resolvableApiException.startResolutionForResult(DriverMapsActivity.this, CHECK_SETTINGS_CODE);
//                        } catch (IntentSender.SendIntentException ex) {
//                            ex.printStackTrace();
//                        }
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        String message = "Adjust location settings in your device";
//                        Toast.makeText(DriverMapsActivity.this, message, Toast.LENGTH_SHORT).show();
//                        isLocationUpdatesActive = false;
//                }
//                updateLocationUi();
//            }
//        });
//
//    }


}