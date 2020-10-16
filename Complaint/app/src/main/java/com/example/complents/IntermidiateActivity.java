package com.example.complents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IntermidiateActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_intermidiate);

    }


    //Method for Gov Worker

    public void govWorker( View view){


        Intent intent = new Intent (IntermidiateActivity.this,govSignIn.class);
        startActivity (intent);
        DataModel.typeOfUser = "govW";



    }


    //Method for citizen

    public void goCitizen(View view){

        Intent intent = new Intent (IntermidiateActivity.this,signIn.class);
        startActivity (intent);
        DataModel.typeOfUser = "citi";

    }


}
