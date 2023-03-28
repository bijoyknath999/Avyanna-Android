package com.dev.avyanna.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.dev.avyanna.R;
import com.dev.avyanna.activity.LawyerListActivity;
import com.dev.avyanna.activity.SectionsActivity;
import com.dev.avyanna.activity.WomenRightsActivity;

public class HomeFragment extends Fragment {

    private CardView Police, Ambulance, Lawyer, Fire_Station, MedicalHelpLine, ChildHelpLine, Women_Rights, Sections;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Police = view.findViewById(R.id.home_police_click);
        Ambulance = view.findViewById(R.id.home_ambulance_click);
        Lawyer = view.findViewById(R.id.home_lawyer_click);
        Fire_Station = view.findViewById(R.id.home_fire_station_click);
        MedicalHelpLine = view.findViewById(R.id.home_medical_helpline_click);
        ChildHelpLine = view.findViewById(R.id.home_child_helpline_click);
        Women_Rights = view.findViewById(R.id.home_women_rights_click);
        Sections = view.findViewById(R.id.home_sections_click);

        Police.setOnClickListener(view1 -> {
            Uri u = Uri.parse("tel:1091");
            Intent policecall = new Intent(Intent.ACTION_DIAL, u);
            try
            {
                startActivity(policecall);
            }
            catch (SecurityException s)
            {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });

        Ambulance.setOnClickListener(view12 -> {
            Uri u = Uri.parse("tel:102");
            Intent policecall = new Intent(Intent.ACTION_DIAL, u);
            try
            {
                startActivity(policecall);
            }
            catch (SecurityException s)
            {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });

        Lawyer.setOnClickListener(
                view13 -> startActivity(new Intent(getContext(), LawyerListActivity.class)));

        Fire_Station.setOnClickListener(view14 ->
        {
            Uri u = Uri.parse("tel:101");
            Intent fireservicecall = new Intent(Intent.ACTION_DIAL, u);
            try
            {
                startActivity(fireservicecall);
            }
            catch (SecurityException s)
            {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });

        MedicalHelpLine.setOnClickListener(view15 -> {
            Uri u = Uri.parse("tel:9830079999");
            Intent medicalhelpcall = new Intent(Intent.ACTION_DIAL, u);
            try
            {
                startActivity(medicalhelpcall);
            }
            catch (SecurityException s)
            {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });

        ChildHelpLine.setOnClickListener(view16 -> {
            Uri u = Uri.parse("tel:1098");
            Intent childhelpcall = new Intent(Intent.ACTION_DIAL, u);
            try
            {
                startActivity(childhelpcall);
            }
            catch (SecurityException s)
            {
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        });

        Women_Rights.setOnClickListener(view17 -> startActivity(new Intent(getContext(), WomenRightsActivity.class)));

        Sections.setOnClickListener(view18 -> startActivity(new Intent(getContext(), SectionsActivity.class)));

        return view;
    }
}