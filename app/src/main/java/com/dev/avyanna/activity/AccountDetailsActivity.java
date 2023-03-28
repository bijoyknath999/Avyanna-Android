package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.dev.avyanna.models.User;
import com.dev.avyanna.utils.AppUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

public class AccountDetailsActivity extends AppCompatActivity {

    private TextInputEditText Username, FullName, Number, Address, Bio;
    private TextInputLayout UsernameLayout, FullnameLayout, NumberLayout, AddressLayout, BioLayout;
    private PowerSpinnerView GenderSpin;
    private MaterialButton SubmitButton;
    private String username, fullname, number, address, gender, bio, CurrentUser, usertype;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userdatabase, userUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        Username = findViewById(R.id.account_details_username);
        FullName = findViewById(R.id.account_details_name);
        Number = findViewById(R.id.account_details_number);
        Address = findViewById(R.id.account_details_address);
        Bio = findViewById(R.id.account_details_bio);
        GenderSpin = findViewById(R.id.account_details_gender_spin);
        SubmitButton = findViewById(R.id.account_details_submit);
        UsernameLayout = findViewById(R.id.account_details_username_layout);
        FullnameLayout = findViewById(R.id.account_details_name_layout);
        NumberLayout = findViewById(R.id.account_details_number_layout);
        AddressLayout = findViewById(R.id.account_details_address_layout);
        BioLayout = findViewById(R.id.account_details_bio_layout);

        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser().getUid();
        usertype = AppUtils.getInstance(AccountDetailsActivity.this).getUserType();

        firebaseDatabase = FirebaseDatabase.getInstance();
        userdatabase = firebaseDatabase.getReference().child("Users").child(CurrentUser);
        userUser = firebaseDatabase.getReference().child("Users");


        if (usertype.equals("lawyer"))
        {
            BioLayout.setVisibility(View.VISIBLE);
        }

        GenderSpin.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int oldIndex, @Nullable String oldItem, int newIndex, String newItem) {
                gender = newItem;
                Toast.makeText(getApplicationContext(), ""+gender, Toast.LENGTH_LONG).show();
            }
        });


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = Username.getText().toString();
                fullname = FullName.getText().toString();
                number = Number.getText().toString();
                address = Address.getText().toString();
                if (usertype.equals("lawyer"))
                    bio = Bio.getText().toString();
                else
                    bio = "null";

                if (!username.isEmpty() &&
                        !fullname.isEmpty() &&
                        !number.isEmpty() &&
                        !gender.isEmpty() &&
                        !address.isEmpty() &&
                        !bio.isEmpty())
                {
                    Query query = userUser.orderByChild("username").equalTo(username);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                UsernameLayout.setError("Username already exist");
                                FullnameLayout.setError(null);
                                NumberLayout.setError(null);
                                AddressLayout.setError(null);
                                BioLayout.setError(null);
                            }
                            else
                            {
                                ProfileCreate();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                    {
                        if (username.isEmpty())
                        {
                            UsernameLayout.setError("Enter your username");
                            FullnameLayout.setError(null);
                            NumberLayout.setError(null);
                            AddressLayout.setError(null);
                            BioLayout.setError(null);
                        }
                        else if (fullname.isEmpty())
                        {
                            UsernameLayout.setError(null);
                            FullnameLayout.setError("Enter your fullname");
                            NumberLayout.setError(null);
                            AddressLayout.setError(null);
                            BioLayout.setError(null);
                        }
                        else if (number.isEmpty())
                        {
                            UsernameLayout.setError(null);
                            FullnameLayout.setError(null);
                            NumberLayout.setError("Enter your number");
                            AddressLayout.setError(null);
                            BioLayout.setError(null);
                        }
                        else if (gender.isEmpty())
                        {
                            UsernameLayout.setError(null);
                            FullnameLayout.setError(null);
                            NumberLayout.setError(null);
                            AddressLayout.setError(null);
                            BioLayout.setError(null);
                            Toast.makeText(getApplicationContext(), "Please Select Gender!!", Toast.LENGTH_LONG).show();
                        }
                        else if (address.isEmpty())
                        {
                            UsernameLayout.setError(null);
                            FullnameLayout.setError(null);
                            NumberLayout.setError(null);
                            AddressLayout.setError("Enter your full address");
                            BioLayout.setError(null);
                        }
                        else if (bio.isEmpty())
                        {
                            UsernameLayout.setError(null);
                            FullnameLayout.setError(null);
                            NumberLayout.setError(null);
                            AddressLayout.setError(null);
                            BioLayout.setError("Enter your bio");
                        }
                    }
            }
        });


    }

    private void ProfileCreate()
    {
        if (!CurrentUser.isEmpty())
        {
            User user = new User(username, fullname, number, address, gender, bio, usertype,CurrentUser);
            if (!usertype.isEmpty())
            {
                userdatabase.setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                SendUserToMainActivity();
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error "+e, Toast.LENGTH_LONG).show();
                            }
                        });
            }

        }
    }

    private void SendUserToMainActivity()

    {
        Intent MainIntent = new Intent(AccountDetailsActivity.this, MainActivity.class);
        MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(MainIntent);
        finish();
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}