package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleLawyerActivity extends AppCompatActivity {
    private TextView TextName, TextUsername, TextBio, TextNUmber, TextAddress;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabase;
    private String UID;
    private Toolbar toolbar;
    private CircleImageView ProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_lawyer);

        UID = getIntent().getStringExtra("uid");
        TextName = findViewById(R.id.single_lawyer_name);
        TextUsername = findViewById(R.id.single_lawyer_username);
        ProfileImage = findViewById(R.id.single_lawyer_image);
        TextBio = findViewById(R.id.single_lawyer_bio);
        TextNUmber = findViewById(R.id.single_lawyer_number);
        TextAddress = findViewById(R.id.single_lawyer_address);

        toolbar = findViewById(R.id.toolbar_single_lawyer);
        toolbar.setTitle("Lawyer Details");
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_back);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabase = firebaseDatabase.getReference().child("Users").child(UID);

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String name, username, gender, bio, number, address;
                    name = snapshot.child("fullname").getValue().toString();
                    username = snapshot.child("username").getValue().toString();
                    gender = snapshot.child("gender").getValue().toString();
                    bio = snapshot.child("bio").getValue().toString();
                    number = snapshot.child("number").getValue().toString();
                    address = snapshot.child("address").getValue().toString();

                    TextName.setText(name);
                    TextUsername.setText(username);
                    TextBio.setText(bio);
                    TextNUmber.setText("+91"+number);
                    TextAddress.setText(address);

                    if (gender.equals("Male"))
                    {
                        ProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.male_lawyer));
                    }
                    else
                    {
                        ProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.female_lawyer));
                    }

                    TextNUmber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri u = Uri.parse("tel:+91"+number);
                            Intent call = new Intent(Intent.ACTION_DIAL, u);
                            try
                            {
                                startActivity(call);
                            }
                            catch (SecurityException s)
                            {
                                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    TextAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                            mapIntent.setData(Uri.parse("google.navigation:q="+address));
                            if (mapIntent.resolveActivity(getApplicationContext().getPackageManager())!= null)
                            {
                                startActivity(mapIntent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"You Don't have any navigation or map apps!!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error "+error, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}