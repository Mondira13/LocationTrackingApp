package com.example.admin.mymapapp;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btnSearch;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(google_service_available()){
            Toast.makeText(this,"Google Service Available",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            init();
        }else{

        }
    }


    public boolean google_service_available(){
        GoogleApiAvailability apiAvailability=GoogleApiAvailability.getInstance();
        int isAvailable=apiAvailability.isGooglePlayServicesAvailable(this);
        if(isAvailable== ConnectionResult.SUCCESS){
            return true;
        }else{
            if(apiAvailability.isUserResolvableError(isAvailable)){
                Dialog dialog=apiAvailability.getErrorDialog(this,isAvailable,0);
                dialog.show();
            }else{
                Toast.makeText(this,"Fail to connect Play service",Toast.LENGTH_LONG).show();
            }
        }

        return false;
    }

    private void init(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSearch=findViewById(R.id.btnSearch);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        gotoLocation(22.58017599,88.43529152,10);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(22.58017599,88.43529152);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void gotoLocation(Double lat,Double lon){
        LatLng latLng=new LatLng(lat,lon);
        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLng(latLng);
        mMap.animateCamera(cameraUpdate);
    }

    private void gotoLocationZoom(Double lat,Double lon,float zoom){
        LatLng latLng=new LatLng(lat,lon);
        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.animateCamera(cameraUpdate);
    }

    public void searchLocation(View view) throws IOException {
        etSearch=findViewById(R.id.etSearch);
        String location=etSearch.getText().toString();

        Geocoder geocoder=new Geocoder(this);  // Geocoder class convert latitude & longitude to meaning full location address
        List<Address> list=geocoder.getFromLocationName(location,1);  // through geocoder reference variable get locations and stored the list of address into addressList
        Address address=list.get(0);
        String locality=address.getLocality(); // through address reference variable get country name & locality name then store it string type variable(locality)
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat=address.getLatitude(); // its get lattitude value of a particular location
        double lng=address.getLongitude(); // its get longnitude value of a particular location
        gotoLocationZoom(lat,lng,10);  // gotoLocationZoom() method is a my own created method and pass the current location lattitude & longnitude value & zooming level through this method
        LatLng latLng=new LatLng(lat,lng); // instantiate the class, LatLng
        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//        mMap.addMarker(new MarkerOptions().position(latLng).title(locality).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }
}
