package com.example.complents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class complentSelect extends AppCompatActivity {
    String a[] = {"Select Complain" , "Garbage On Road" , "Death animal" , "Other"};


    Spinner spinner;
    String complain;
    EditText complaneBox;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_complent_select );
        spinner = findViewById ( R.id.spinner3 );
        ArrayAdapter <String> arrayAdapter2 = new ArrayAdapter <> ( this , android.R.layout.simple_spinner_item , a );
        arrayAdapter2.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter ( arrayAdapter2 );
        complaneBox = findViewById ( R.id.complentBox );
        spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected( AdapterView <?> parent , View view , int position , long id ) {
                if (position != 0) {
                    complain = a[position];
                }
            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        } );

    }

    public void next( View view ) {
try {

    if (DataModel.typeOfUser.equals ( "citi" )) {


        if (complain != null) {
            DataModel.innerComplan = complain;
            if (complaneBox.getText ( ) != null) {
                DataModel.comments = complaneBox.getText ( ).toString ( );
            }

            Intent intent = new Intent ( complentSelect.this , MapsActivity.class );
            startActivity ( intent );

        } else {

            Toast.makeText ( complentSelect.this , "Please! select Complane" , Toast.LENGTH_LONG ).show ( );

        }


//    }else if(DataModel.typeOfUser.equals ( "govW" )){
//
//
//Intent intent = new Intent ( complentSelect.this,complainSHow.class );
//startActivity ( intent );
//
//
//
//    }

    }

}catch (Exception e){



}


    }
}