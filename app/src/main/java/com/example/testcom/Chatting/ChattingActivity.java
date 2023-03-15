package com.example.testcom.Chatting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testcom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChattingAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private EditText mSendEditText;
    private ImageButton mSendButton;
    private CircleImageView mProfileImage;
    private TextView mUserName;

    DatabaseReference mDatabaseUser, mDatabaseChat, allusersDb,mPartnerReference;
    private String currentUserId,chatId,chatlistid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Toolbar toolbar = findViewById(R.id.toolBarChat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener((v -> {finish();}));
        mProfileImage = findViewById(R.id.profileImageChat);
        mUserName = findViewById(R.id.username_chat);
        allusersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        chatlistid = getIntent().getExtras().getString("chatListId");
        mPartnerReference = allusersDb.child(chatlistid);
        mPartnerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String partnerName = "";
                    String partnerImageUrl = "";
                    if (snapshot.child("name").getValue() != null){
                        partnerName = snapshot.child("name").getValue().toString();
                        mUserName.setText(partnerName);
                    }
                    Glide.clear(mProfileImage);
                    if (snapshot.child("profileImageUrl").getValue() != null){
                        partnerImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                        switch (partnerImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(partnerImageUrl).into(mProfileImage);
                                break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).
                child("Chatlist").child(chatlistid).child("ChatId");

        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");


        getChatId();

        mRecyclerView = (RecyclerView) findViewById(R.id.chatting_recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(ChattingActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);


        mChattingAdapter = new ChattingAdapter(getDataSetChat(),ChattingActivity.this);
        mRecyclerView.setAdapter(mChattingAdapter);

        mSendEditText = findViewById(R.id.message);
        mSendButton = findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String sendMessageText = mSendEditText.getText().toString();

        if (!sendMessageText.isEmpty()){
            allusersDb.child(chatlistid).child("Chatlist").child(currentUserId).setValue(true);
            allusersDb.child(chatlistid).child("Chatlist").child(currentUserId).child("ChatId").setValue(chatId);

            DatabaseReference newMessageDb = mDatabaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("createdByUser",currentUserId);
            newMessage.put("text",sendMessageText);

            newMessageDb.setValue(newMessage);
        }
        mSendEditText.setText(null);
    }

    private List<ChattingModel> getDataSetChat() {
        return resultsChat;
    }

    private void getChatId() {
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    chatId = snapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);
                    getChatMessages();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    String message = null;
                    String createdByUser = null;
                    if (snapshot.child("text").getValue() != null){
                        message = snapshot.child("text").getValue().toString();
                    }
                    if (snapshot.child("createdByUser").getValue() != null){
                        createdByUser = snapshot.child("createdByUser").getValue().toString();
                    }
                    if (message != null && createdByUser != null){
                        Boolean currentUserBoolean = false;
                        if (createdByUser.equals(currentUserId)){
                            currentUserBoolean = true;
                        }
                        ChattingModel newMessage = new ChattingModel(message,currentUserBoolean);
                        resultsChat.add(newMessage);
                        mChattingAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private ArrayList<ChattingModel> resultsChat = new ArrayList<>();
}