package com.example.complents;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class complainSHow extends AppCompatActivity {



    ListView listView;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_complain_show );
        final ArrayList<String> complainArray = new ArrayList <> (  );
        final ArrayList<String> commentsArray = new ArrayList <> (  );
        listView = findViewById ( R.id.complainList );

        DatabaseReference reference = FirebaseDatabase.getInstance ( ).getReference ( ).child ( "Complan" );


        reference.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {




                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    HashMap<String,String> map = (HashMap<String, String> ) postSnapshot.getValue ();

                    String compla = map.get ( "complan" );
                    String comm = map.get ( "comments" );
                    complainArray.add ( compla );
                    commentsArray.add ( comm );




                }
//

                ArrayAdapter adapter = new ArrayAdapter ( complainSHow.this,android.R.layout.simple_list_item_1,complainArray.toArray () );
                listView.setAdapter ( adapter );
                listView.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
                    @Override
                    public void onItemClick( AdapterView <?> parent , View view , int position , long id ) {

                        Toast.makeText ( complainSHow.this,commentsArray.get ( position ),Toast.LENGTH_LONG ).show ();


                    }
                } );




            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );















    }
}
