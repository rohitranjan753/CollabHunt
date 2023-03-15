package com.example.testcom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testcom.Chat.chatAdapter;
import com.example.testcom.Chat.chatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView chatRecyclerview;
    private RecyclerView.Adapter adapterChat;
    private FirebaseAuth mAuth;

    private String currentUid;
    private DatabaseReference usersDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat,container,false);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        currentUid = mAuth.getCurrentUser().getUid();
        chatRecyclerview = v.findViewById(R.id.chatRecyclerview);
        chatRecyclerview.setNestedScrollingEnabled(false);
        chatRecyclerview.setHasFixedSize(true);
        chatRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterChat = new chatAdapter(getDataChat(),getContext());
        chatRecyclerview.setAdapter(adapterChat);
        getUserChatList();
        return v;

    }
    private void getUserChatList() {
        DatabaseReference chatlistDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid).child("Chatlist");
        chatlistDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot list : snapshot.getChildren()){
                        FetchUser(list.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void FetchUser(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String uid = "";
                    uid = snapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    if (snapshot.child("name").getValue() != null){
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    chatModel obj = new chatModel(name, profileImageUrl, uid);
                    resultsMatches.add(obj);
                    adapterChat.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<chatModel> getDataChat() {
        return resultsMatches;
    }
    private ArrayList<chatModel> resultsMatches = new ArrayList<chatModel>();
}