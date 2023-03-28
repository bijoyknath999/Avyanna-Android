package com.dev.avyanna.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.dev.avyanna.utils.AppUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSelection extends AppCompatActivity {

    private TextView AsAUser, AsALawyer;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        AsAUser = findViewById(R.id.user_select_as_user);
        AsALawyer = findViewById(R.id.user_select_as_lawyer);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        AsAUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.getInstance(UserSelection.this).setUserType("user");
                Intent intentUser = new Intent(UserSelection.this,LoginActivity.class);
                startActivity(intentUser);
            }
        });

        AsALawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.getInstance(UserSelection.this).setUserType("lawyer");
                Intent intentUser = new Intent(UserSelection.this,LoginActivity.class);
                startActivity(intentUser);
            }
        });
    }

    protected void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

}