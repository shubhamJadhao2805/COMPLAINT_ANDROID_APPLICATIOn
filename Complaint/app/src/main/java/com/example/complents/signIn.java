package com.example.complents;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signIn extends AppCompatActivity {

    ProgressBar progressBar;
    EditText username;
    EditText password;
    View view3;
    FirebaseAuth auth;





    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_in);

        username = findViewById (R.id.username1);
        password = findViewById (R.id.password1);
        progressBar = findViewById (R.id.progressBar2);
        view3 = findViewById (R.id.view3);
        auth = FirebaseAuth.getInstance ();



    }


    //Method to signUp

    public void  signUp( final View view){

        progressBar.setVisibility (View.VISIBLE);
        view3.setVisibility (View.VISIBLE);

        auth.createUserWithEmailAndPassword (username.getText ().toString (),password.getText ().toString ()).addOnCompleteListener (new OnCompleteListener <AuthResult> ( ) {
            @Override
            public void onComplete( @NonNull Task<AuthResult> task ) {
                if(task.isSuccessful ()){
                    Toast.makeText (signIn.this,"New User created!",Toast.LENGTH_SHORT).show ();
                    progressBar.setVisibility (View.INVISIBLE);
                    view3.setVisibility (View.INVISIBLE);
                    DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ( auth.getCurrentUser ().getUid ());
                    reference.setValue ( DataModel.typeOfUser );



                }else{
                    Toast.makeText (signIn.this,"Failed to create User!",Toast.LENGTH_SHORT).show ();
                    progressBar.setVisibility (View.INVISIBLE);
                    view3.setVisibility (View.INVISIBLE);
                }
            }
        });

    }


    //Method to logIn page


    public void logIn(View view){

        Intent intent = new Intent (signIn.this,LogInActivity.class);
        startActivity (intent);



    }


}
