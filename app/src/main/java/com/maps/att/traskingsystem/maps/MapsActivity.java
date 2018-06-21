package com.maps.att.traskingsystem.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maps.att.traskingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //multiple
    private static final LatLng JAKTIM=new LatLng(-6.225014,106.900447);
    private static final LatLng JAKUT=new LatLng(-6.186486,106.834091);
    private static final LatLng JAKSEL=new LatLng(-6.261493,106.810600);

    private Marker mJaktim,mJakut,mJaksel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupLocation();
    }

    private void setupLocation() {
        if(android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED)
        {
            requestRuntimePermission();
        }
        else
        {
            if(checkPlaySErvices())
            {
                buildGoogleApiCLient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    private void displayLocation() {

    }

    private void createLocationRequest() {

    }

    private void buildGoogleApiCLient() {

    }

    private boolean checkPlaySErvices() {
        return false;
    }

    private void requestRuntimePermission() {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Marker> markerlist=new ArrayList<>();

        mJaktim=mMap.addMarker(new MarkerOptions()
                .position(JAKTIM)
                .title("JAKTIM")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mJaktim.setTag(0);
        markerlist.add(mJaktim);

        mJaksel=mMap.addMarker(new MarkerOptions()
                .position(JAKSEL)
                .title("JAKSEL")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mJaksel.setTag(0);
        markerlist.add(mJaksel);

        mJakut=mMap.addMarker(new MarkerOptions()
                .position(JAKUT)
                .title("JAKUT")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mJakut.setTag(0);
        markerlist.add(mJakut);

        for(Marker m:markerlist)
        {
            LatLng latlng=new LatLng(m.getPosition().latitude,m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latlng).title(m.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        }

        // Add a marker in jakarta and move the camera DEFAULT
        LatLng jakarta=new LatLng(-6.1362558,106.7287387);
        mMap.addMarker(new MarkerOptions().position(jakarta).title("Jakarta").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((jakarta),12.0f));



    }
}
