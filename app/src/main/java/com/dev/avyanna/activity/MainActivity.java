package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dev.avyanna.ChatBotActivity;
import com.dev.avyanna.fragment.SettingsFragment;
import com.dev.avyanna.fragment.HomeFragment;
import com.dev.avyanna.R;
import com.dev.avyanna.utils.AppUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference UserRef;
    private FirebaseUser User;
    private String UserID, UserType;
    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        User = mAuth.getCurrentUser();
        if (User !=null)
        {
            UserID = User.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            UserRef = firebaseDatabase.getReference().child("Users");
        }

        UserType = AppUtils.getInstance(MainActivity.this).getUserType();

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_category));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_settings));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent chatIntent = new Intent(getApplicationContext(), ChatBotActivity.class);
                startActivity(chatIntent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {

                switch (itemIndex)
                {
                    case 0:
                        selectedFragment = new HomeFragment();
                        break;
                    case 1:
                        selectedFragment = new SettingsFragment();
                        break;
                }

                if (selectedFragment != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_container,selectedFragment).commit();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemIndex)
                {
                    case 0:
                        selectedFragment = new HomeFragment();
                        break;
                    case 1:
                        selectedFragment = new SettingsFragment();
                        break;
                }

                if (selectedFragment != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_container,selectedFragment).commit();
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,
                new HomeFragment()).commit();

    }

    @Override
    public void onStart() {
        super.onStart();

        if (User!=null) {
           CheckUserExistence();
        }
    }

    private void CheckUserExistence()

    {
        final String current_user_id = mAuth.getCurrentUser().getUid();

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.hasChild(current_user_id))
                {
                    if (!UserType.isEmpty())
                    {
                        SendUserSetupAccount();
                    }
                    else
                    {
                        SignOutMove();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }



    private void SendUserSetupAccount() {
        Intent SetupAccountIntent = new Intent(MainActivity.this, AccountDetailsActivity.class);
        SetupAccountIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(SetupAccountIntent);
        finish();
    }

    private void SignOutMove()
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,UserSelection.class));
        finish();
    }
}