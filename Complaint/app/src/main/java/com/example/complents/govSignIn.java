package com.example.complents;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class govSignIn extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText govId;
    Spinner spinner;
    FirebaseAuth myAuth;
    DatabaseReference databaseReference;
    String regionSelected;
    ProgressBar progressBar;
    View view4;




    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_gov_sign_in);

        username = findViewById (R.id.username);
        password = findViewById (R.id.password);
        spinner = findViewById (R.id.spinner2);
        govId = findViewById (R.id.goverId);
        myAuth = FirebaseAuth.getInstance ();
        databaseReference = FirebaseDatabase.getInstance ().getReference ();
        progressBar = findViewById (R.id.progressBar3);
        view4 = findViewById (R.id.view4);

       final String a[] = {"Select your Region","Aurangabad","Washim","Mumbai"};
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter <> (this,android.R.layout.simple_spinner_item,a);
        arrayAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter (arrayAdapter);
        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected( AdapterView <?> parent, View view, int position, long id ) {
                if(position != 0){
                    regionSelected = a[position];
                }

            }

            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        });


    }

    //SignIn method

    public void govSignIn( View view){
        progressBar.setVisibility (View.VISIBLE);
        view.setVisibility (View.VISIBLE);
        if(username != null && password != null && regionSelected != null) {
            myAuth.createUserWithEmailAndPassword (username.getText ( ).toString ( ), password.getText ( ).toString ( )).addOnCompleteListener (new OnCompleteListener <AuthResult> ( ) {
                @Override
                public void onComplete( @NonNull Task <AuthResult> task ) {


                    if (task.isSuccessful ( )) {

                        progressBar.setVisibility (View.INVISIBLE);
                        view4.setVisibility (View.INVISIBLE);
                        savePersonalInfo ( );
                        Intent intent = new Intent (govSignIn.this, LogInActivity.class);
                        startActivity (intent);
                        Toast.makeText (govSignIn.this, "New user Created", Toast.LENGTH_SHORT).show ( );

                    } else {

                        Toast.makeText (govSignIn.this, task.getException ().toString (), Toast.LENGTH_SHORT).show ( );
                        progressBar.setVisibility (View.INVISIBLE);
                        view4.setVisibility (View.INVISIBLE);
                    }

                }
            });

        }else{

            Toast.makeText (govSignIn.this,"Enter details",Toast.LENGTH_SHORT).show ();

        }

    }

    //LogIn metod
    public void logIn(View view){

        Intent intent = new Intent ();
        startActivity (intent);

    }

    //Method to save personal Information

    public void savePersonalInfo(){

        DatabaseReference ref = databaseReference.child (myAuth.getCurrentUser ().getUid ());
        ref.setValue ( DataModel.typeOfUser );

    }



}
