package com.example.testcom.Chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcom.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatViewHolder extends RecyclerView.ViewHolder {
    CircleImageView mImage;
    TextView mName;

    public chatViewHolder(@NonNull View itemView) {
        super(itemView);
        mImage = (CircleImageView) itemView.findViewById(R.id.chat_img);
        mName = (TextView) itemView.findViewById(R.id.chat_name);
    }

}
