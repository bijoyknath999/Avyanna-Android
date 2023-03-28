package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.dev.avyanna.adapter.SectionsAdpter;
import com.dev.avyanna.model.SectionsModel;
import com.dev.avyanna.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class SectionsActivity extends AppCompatActivity {

    private List<SectionsModel> sectionsModel;
    private SectionsAdpter sectionsAdpter;
    private List<String> SectionTitle, SectionDescription;
    private RecyclerView SectionsRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        sectionsModel = new ArrayList<>();

        SectionTitle = ArrayUtils.getSectionstitle(getApplicationContext());
        SectionDescription = ArrayUtils.getSectionsDescription(getApplicationContext());

        toolbar = findViewById(R.id.toolbar_sections);
        toolbar.setTitle("Sections");
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_back);
        LoadSection();

    }

    private void LoadSection() {

        SectionsRecyclerView = findViewById(R.id.recycler_view_sections);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1,RecyclerView.VERTICAL,false);
        SectionsRecyclerView.setLayoutManager(gridLayoutManager);
        sectionsAdpter = new SectionsAdpter(sectionsModel, getApplicationContext());
        SectionsRecyclerView.setAdapter(sectionsAdpter);
        sectionsAdpter.notifyDataSetChanged();
        if (sectionsModel!=null)
        {
            sectionsModel.clear();
        }
        for (int i = 0; i < SectionTitle.size(); i++) {
            SectionsModel sectionsModellist = new SectionsModel(SectionTitle.get(i), SectionDescription.get(i));
            sectionsModel.add(sectionsModellist);
        }

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