package com.dev.avyanna.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.dev.avyanna.activity.AboutUs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private TextView TextName, TextUserName;
    private LinearLayout ClickPrivacy, ClickAboutUs, ClickRateUs;
    private FirebaseAuth mAuth;
    private String CurrentUserID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference UserDatabase;
    private CircleImageView ProfileIMg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextName = view.findViewById(R.id.lawyer_name_settings);
        TextUserName = view.findViewById(R.id.lawyer_username_settings);
        ClickPrivacy = view.findViewById(R.id.privacy_click_settings);
        ClickAboutUs = view.findViewById(R.id.about_us_click_settings);
        ClickRateUs = view.findViewById(R.id.rate_us_click_settings);
        ProfileIMg = view.findViewById(R.id.image_settings);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (user!=null)
        {
            CurrentUserID = user.getUid();
            UserDatabase = firebaseDatabase.getReference().child("Users").child(CurrentUserID);
        }

        UserDatabase.addValueEventListener(new ValueEventListener() {
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
                    TextUserName.setText(username);

                    if (gender.equals("Male"))
                    {
                        ProfileIMg.setImageDrawable(getResources().getDrawable(R.drawable.male_lawyer));
                    }
                    else
                    {
                        ProfileIMg.setImageDrawable(getResources().getDrawable(R.drawable.female_lawyer));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error "+error, Toast.LENGTH_LONG).show();
            }
        });

        ClickPrivacy.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/13vHK597Df8r-bVdK9bVJGBzf9O1jKVaMdUn02AjyfBM/edit?usp=sharing"));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        ClickAboutUs.setOnClickListener(view12 -> {
            Intent aboutIntent = new Intent(getContext(), AboutUs.class);
            startActivity(aboutIntent);
        });

        ClickRateUs.setOnClickListener(view13 -> {
            try
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getContext().getPackageName())));
            }
            catch (ActivityNotFoundException e)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getContext().getPackageName())));
            }
        });

        return view;
    }
}