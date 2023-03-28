package com.dev.avyanna.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.avyanna.R;
import com.dev.avyanna.activity.SingleLawyerActivity;
import com.dev.avyanna.model.LawyerModel;

import java.util.List;

public class LawyerAdapter extends RecyclerView.Adapter<LawyerAdapter.MyViewAapter> {
    private final List<LawyerModel> lawyerModels;
    private final Context mContext;

    public LawyerAdapter(List<LawyerModel> lawyerModels, Context mContext) {
        this.lawyerModels = lawyerModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LawyerAdapter.MyViewAapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lawyer_item, parent, false);
        return new LawyerAdapter.MyViewAapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LawyerAdapter.MyViewAapter holder, int position) {

        LawyerModel lawyerModel = lawyerModels.get(position);
        holder.textname.setText(lawyerModel.getFullname());
        holder.textusername.setText(lawyerModel.getUsername());
        holder.textaddress.setText(lawyerModel.getAddress());
        holder.LawyerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlelawyer = new Intent(mContext, SingleLawyerActivity.class);
                singlelawyer.putExtra("uid", lawyerModel.getUid());
                singlelawyer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(singlelawyer);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lawyerModels.size();
    }

    public class MyViewAapter extends RecyclerView.ViewHolder {
        private RelativeLayout LawyerClick;
        private TextView textname, textusername, textaddress;
        public MyViewAapter(@NonNull View itemView) {
            super(itemView);

            LawyerClick = itemView.findViewById(R.id.lawyer_click);
            textname = itemView.findViewById(R.id.lawyer_name);
            textusername = itemView.findViewById(R.id.lawyer_username);
            textaddress = itemView.findViewById(R.id.lawyer_address);

        }
    }
}
