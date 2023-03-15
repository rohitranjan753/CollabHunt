package com.example.testcom.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcom.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    ImageView mImage;
    TextView mName, mAreaOfInterest, mState;
    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        mImage = (ImageView) itemView.findViewById(R.id.homeImage);
        mName = (TextView)itemView.findViewById(R.id.homeName);
        mAreaOfInterest = (TextView)itemView.findViewById(R.id.homeInterest);
        mState = (TextView)itemView.findViewById(R.id.homeState);
    }
}
