package com.example.testcom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testcom.Home.HomeAdapter;
import com.example.testcom.Home.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseAuth mAuth;
    private String currentUid;
    private DatabaseReference usersDb;
    private String currentUserInterestString;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeAdapter(getData(), getContext());
        recyclerView.setAdapter(adapter);
        getCurrentUserInterest();
        FetchProfileUserInformation();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return v;
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    break;
                case ItemTouchHelper.RIGHT:
                    Model personInfo = (Model) resultsMatches.get(position) ;
                    String matchid = personInfo.getrUid();
                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();
                    usersDb.child(currentUid).child("Chatlist").child(matchid).setValue(true);
                    usersDb.child(currentUid).child("Chatlist").child(matchid).child("ChatId").setValue(key);
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    };

    private void getCurrentUserInterest() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("Area of interest").getValue() != null){
                        currentUserInterestString = snapshot.child("Area of interest").getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void FetchProfileUserInformation() {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.exists() && dataSnapshot.child("Area of interest").getValue()!=null && dataSnapshot
                            .child("Area of interest").getValue().toString().equals(currentUserInterestString) && !dataSnapshot.getKey()
                            .equals(currentUid)){

                        String areainterest = "";
                        String loca = "";
                        String name = "";
                        String profileImageUrl = "";
                        String uid = "";
                        uid = dataSnapshot.getKey();

                        if (dataSnapshot.child("name").getValue() != null){
                            name = dataSnapshot.child("name").getValue().toString();
                        }
                        if (dataSnapshot.child("profileImageUrl").getValue() != null){
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        if (dataSnapshot.child("Area of interest").getValue() != null){
                            areainterest = dataSnapshot.child("Area of interest").getValue().toString();
                        }
                        if (dataSnapshot.child("State").getValue() != null){
                            loca = dataSnapshot.child("State").getValue().toString();
                        }

                        Model obj = new Model(name,areainterest,loca,profileImageUrl,uid);
                        resultsMatches.add(obj);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private ArrayList<Model> resultsMatches = new ArrayList<Model>();
    private List<Model> getData() {
        return resultsMatches;
    }
}