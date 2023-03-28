package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.dev.avyanna.adapter.LawyerAdapter;
import com.dev.avyanna.model.LawyerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LawyerListActivity extends AppCompatActivity {

    private List<LawyerModel> lawyerModels;
    private LawyerAdapter lawyerAdapter;
    private RecyclerView LawyersRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference lawyerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_list);

        toolbar = findViewById(R.id.toolbar_lawyer);
        toolbar.setTitle("Laywers List");
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_back);

        lawyerModels = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        lawyerDatabase = firebaseDatabase.getReference().child("Users");
        LawyersRecyclerView = findViewById(R.id.recycler_view_lawyer);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,RecyclerView.VERTICAL,false);
        LawyersRecyclerView.setLayoutManager(gridLayoutManager);

        loadLawyers();
    }

    private void loadLawyers() {

        Query query = lawyerDatabase.orderByChild("usertype").equalTo("lawyer");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lawyerModels.clear();
                if (snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                        LawyerModel lawyerModel = dataSnapshot1.getValue(LawyerModel.class);

                        lawyerModels.add(lawyerModel);
                    }
                    lawyerAdapter = new LawyerAdapter(lawyerModels, getApplicationContext());

                    LawyersRecyclerView.setAdapter(lawyerAdapter);
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