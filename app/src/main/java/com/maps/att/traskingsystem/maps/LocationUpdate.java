package com.maps.att.traskingsystem.maps;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maps.att.traskingsystem.Manifest;
import com.maps.att.traskingsystem.R;

public class LocationUpdate extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, LocationListener,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    private Location location;
    private LocationRequest mLocationRequest;

    private LocationListener locationListener;
    LocationManager locationManager;
    private Handler mHandler;

    //tambahan refrsesh
    private static int UPDATE_INTERVAL=5000;
    private static int FASTEST_INTERVAL=3000;
    private static int DISTANCE=2;
    private static final int PLAY_SERVICES_RES_REQUEST=7172;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);

//        this.mHandler = new Handler();
//        this.mHandler.postDelayed(m_Runnable, 5000);


        if(checkPlayServices())
        {
            buildGoogleApiCLient();
            createLocationRequest();
//            displayLocation();
        }

    }

    private void buildGoogleApiCLient() {
        googleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
    }
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }


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
        checkLocationandAddToMap();
    }

    @Override
    public void onConnected(Bundle bundle) {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkLocationandAddToMap();
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) LocationUpdate.this);

                } else
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private void checkLocationandAddToMap() {
        //Checking if the user has granted the permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        //Fetching the last known location using the Fus
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        mMap.clear();

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((jakarta),12.0f));
        LatLng jakarta = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(jakarta).title("Posisi saya"+location.getLatitude() + "dann " + location.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((jakarta), 15.0f));
        Log.d("My Location is =====> ", location.toString());
        Toast.makeText(this, "alamat saya" + location.getLatitude() + "dann " + location.getLongitude(), Toast.LENGTH_LONG).show();

    }


    @Override
    public void onLocationChanged(Location location) {
        location=location;
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    //autorefresh avtivity ini
    private final Runnable m_Runnable = new Runnable() {
        public void run()

        {
            if (ActivityCompat.checkSelfPermission(LocationUpdate.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationUpdate.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) LocationUpdate.this);
            checkLocationandAddToMap();
            Toast.makeText(LocationUpdate.this,"in runnable"+location.getLatitude() + "dann " + location.getLongitude(),Toast.LENGTH_SHORT).show();
            LocationUpdate.this.mHandler.postDelayed(m_Runnable, 5000);
        }

    };//runnable

    private void createLocationRequest() {
        mLocationRequest=new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(DISTANCE);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdate() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    //apakah google service terinstall or support
    private boolean checkPlayServices() {
        int resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS)
        {
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RES_REQUEST).show();
            } else
            {
                Toast.makeText(LocationUpdate.this,"this device not support",Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }


}