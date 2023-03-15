package com.example.testcom.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testcom.OnClickProfileDisplayActivity;
import com.example.testcom.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    private List<Model> matchesList;
    private Context context;

    public HomeAdapter(List<Model> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_singlerow_view,
                null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        HomeViewHolder rcv = new HomeViewHolder(view);
        return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final Model temp = matchesList.get(position);
        holder.mName.setText(matchesList.get(position).getrName());
        holder.mAreaOfInterest.setText(matchesList.get(position).getrAreaOfInterest());
        holder.mState.setText(matchesList.get(position).getrState());
        if (!matchesList.get(position).getrProfileImageUrl().equals("default")){
            Glide.with(context).load(matchesList.get(position).getrProfileImageUrl()).into(holder.mImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnClickProfileDisplayActivity.class);
                intent.putExtra("uid",temp.getrUid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.matchesList.size();
    }
}
