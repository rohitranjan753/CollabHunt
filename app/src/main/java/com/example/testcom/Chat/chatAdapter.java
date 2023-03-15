package com.example.testcom.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testcom.Chatting.ChattingActivity;
import com.example.testcom.R;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatViewHolder> {
    private List<chatModel> matchesList;
    private Context context;

    public chatAdapter(List<chatModel> matchesList, Context context) {
        this.matchesList = matchesList;
        this.context = context;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatsinglerow,
                null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        chatViewHolder rcv = new chatViewHolder(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
        final chatModel temp = matchesList.get(position);
        holder.mName.setText(matchesList.get(position).getcName());
        if (!matchesList.get(position).getcProfileImageUrl().equals("default")){
            Glide.with(context).load(matchesList.get(position).getcProfileImageUrl()).into(holder.mImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChattingActivity.class);
                intent.putExtra("chatListId",temp.getcUid());
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
