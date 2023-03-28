package com.dev.avyanna.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.avyanna.R;
import com.dev.avyanna.activity.MainActivity;
import com.dev.avyanna.activity.SingleSectionActivity;
import com.dev.avyanna.model.SectionsModel;

import java.util.List;

public class SectionsAdpter extends RecyclerView.Adapter<SectionsAdpter.MyViewHolder> {

    private final List<SectionsModel> sectionsModels;
    private final Context mContext;

    public SectionsAdpter(List<SectionsModel> sectionsModels, Context mContext) {
        this.sectionsModels = sectionsModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SectionsAdpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sections_item, parent, false);
        return new SectionsAdpter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionsAdpter.MyViewHolder holder, int position) {

        SectionsModel sectionsModel = sectionsModels.get(position);

        holder.TitleTextView.setText(sectionsModel.getTitle());
        holder.ClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SingleSectons = new Intent(mContext, SingleSectionActivity.class);
                SingleSectons.putExtra("title", sectionsModel.getTitle());
                SingleSectons.putExtra("des", sectionsModel.getDescription());
                SingleSectons.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(SingleSectons);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sectionsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TitleTextView;
        private final RelativeLayout ClickView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             TitleTextView = itemView.findViewById(R.id.sections_title);
             ClickView = itemView.findViewById(R.id.sections_click);
        }
    }
}
