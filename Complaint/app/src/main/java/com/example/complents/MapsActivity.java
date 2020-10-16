package com.example.complents;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManagerl;
    LocationListener locationListener;
    MarkerOptions markerOptionsl;
    Marker marker;
    Location locationToSend;

    ProgressBar progressBar;
    View view3;
    String[] innerComplanArray = {"Select your Region","Aurangabad","Washim","Mumbai"};
    Spinner spinner;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        progressBar = findViewById ( R.id.progressBar6 );
        view3 = findViewById ( R.id.view3 );
        setContentView (R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( )
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);
        spinner = findViewById ( R.id.spinner4 );

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter <> ( this,android.R.layout.simple_spinner_item,innerComplanArray );
        arrayAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter ( arrayAdapter );
        spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected( AdapterView <?> parent , View view , int position , long id ) {

                DataModel.regionSelected = innerComplanArray[position];

            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        } );






        locationManagerl = (LocationManager)this.getSystemService ( Context.LOCATION_SERVICE );

        locationListener = new LocationListener ( ) {
            @Override
            public void onLocationChanged( Location location ) {

                locationToSend = location;
                LatLng latLng = new LatLng ( location.getLatitude (),location.getLongitude () );
                marker.setPosition (latLng);


            }

            @Override
            public void onStatusChanged( String provider , int status , Bundle extras ) {

            }

            @Override
            public void onProviderEnabled( String provider ) {

            }

            @Override
            public void onProviderDisabled( String provider ) {

            }
        };

        if(ContextCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions ( this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );


        }else{

            locationManagerl.requestLocationUpdates ( LocationManager.GPS_PROVIDER,0,0,locationListener );
            locationToSend = locationManagerl.getLastKnownLocation ( LocationManager.GPS_PROVIDER );
        }














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
    public void onMapReady( GoogleMap googleMap ) {
        mMap = googleMap;


            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng ( 19.8762 , 75.3433 );
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude ( 19.8762 );
            location.setLongitude (75.3433);
            locationToSend = location;

            markerOptionsl = new MarkerOptions ( ).position ( sydney );

            marker = mMap.addMarker ( markerOptionsl );

            mMap.moveCamera ( CameraUpdateFactory.newLatLng ( sydney ) );



    }


    public void send( View view){


//
//        progressBar.setVisibility (View.VISIBLE);
//        view3.setVisibility ( View.VISIBLE );
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance ( ).getReference ( ).child ( "Complan" );
            HashMap <String, String> map = new HashMap <> ( );
            map.put ( "complan" , DataModel.innerComplan );
            map.put ( "Latitude" , String.valueOf ( locationToSend.getLatitude ( ) ) );
            map.put ( "Longitude" , String.valueOf ( locationToSend.getLongitude ( ) ) );
            map.put ( "comments" , DataModel.comments );
            map.put ( "region",DataModel.regionSelected );

            reference.push ( ).setValue ( map ).addOnCompleteListener ( new OnCompleteListener <Void> ( ) {
                @Override
                public void onComplete( @NonNull Task <Void> task ) {
                    if(task.isSuccessful ()){

//                        progressBar.setVisibility ( View.INVISIBLE );
//                        view3.setVisibility ( View.INVISIBLE );
                        Toast.makeText ( MapsActivity.this,"Complain registered!",Toast.LENGTH_SHORT ).show ();


                    }else{


//                        progressBar.setVisibility ( View.INVISIBLE );
//                        view3.setVisibility ( View.INVISIBLE );
                        Toast.makeText ( MapsActivity.this,"Complain registered Failed!",Toast.LENGTH_SHORT ).show ();


                    }
                }
            } );
        }catch (Exception e){


//            progressBar.setVisibility ( View.INVISIBLE );
//            view3.setVisibility ( View.INVISIBLE );
            Toast.makeText ( MapsActivity.this,"Please Check Location Services!",Toast.LENGTH_SHORT ).show ();


        }

    }


}
