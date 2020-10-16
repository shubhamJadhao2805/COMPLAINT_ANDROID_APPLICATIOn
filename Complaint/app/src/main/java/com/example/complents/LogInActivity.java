package com.example.complents;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    ProgressBar progressBar;
    FirebaseAuth auth;
    View view2;



    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_log_in);


        username = findViewById (R.id.username1);
        password = findViewById (R.id.password1);
        progressBar = findViewById (R.id.progressBar);
        view2 = findViewById (R.id.view2);
        auth = FirebaseAuth.getInstance ();


    }


    //Method to SignIn user

    public void signIn( final View view){
        progressBar.setVisibility (View.VISIBLE);
        view2.setVisibility (View.VISIBLE);
        if(username.getText () != null && password.getText () != null) {
            auth = FirebaseAuth.getInstance ( );
            auth.signInWithEmailAndPassword (username.getText ( ).toString ( ), password.getText ( ).toString ( )).addOnCompleteListener (new OnCompleteListener <AuthResult> ( ) {
                @Override
                public void onComplete( @NonNull Task<AuthResult> task ) {

                    if(task.isSuccessful ()){
try {
    progressBar.setVisibility ( View.INVISIBLE );
    view2.setVisibility ( View.INVISIBLE );
    DatabaseReference reference = FirebaseDatabase.getInstance ( ).getReference ( ).child ( auth.getCurrentUser ( ).getUid ( ) );
    reference.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
        @Override
        public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
            DataModel.typeOfUser = ( String ) dataSnapshot.getValue ( );
            Toast.makeText ( LogInActivity.this , DataModel.typeOfUser , Toast.LENGTH_SHORT ).show ( );
            if(DataModel.typeOfUser.equals ( "govW" )) {


                Intent intent = new Intent ( LogInActivity.this , complainSHow.class );
                startActivity ( intent );

            }else{

                Intent intent = new Intent ( LogInActivity.this , Main2Activity.class );
                startActivity ( intent );


            }


        }

        @Override
        public void onCancelled( @NonNull DatabaseError databaseError ) {

        }
    } );


}catch (Exception e){

    Toast.makeText (LogInActivity.this,"LogIn Failed",Toast.LENGTH_SHORT).show ();
    progressBar.setVisibility (View.INVISIBLE);
    view2.setVisibility (View.INVISIBLE);


}


                    }else{

                        Toast.makeText (LogInActivity.this,"LogIn Failed",Toast.LENGTH_SHORT).show ();
                        progressBar.setVisibility (View.INVISIBLE);
                        view2.setVisibility (View.INVISIBLE);

                    }

                }
            });


        }else{

            Toast.makeText (LogInActivity.this,"Please Enter Password and Username!",Toast.LENGTH_SHORT).show ();


        }

    }

    //Method to signup user

    public void signUp(View view){

        Intent intent = new Intent (LogInActivity.this,IntermidiateActivity.class);
        startActivity (intent);

    }







}
