package com.example.testcom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView mProfileImage;
    private TextView mProfileName, mProfileInterest;
    private TextInputLayout mProfileUsername, mProfilePhoneNo
            , mProfileProfession, mProfileProjects
            , mProfileLocation;
    private Button mEditProfile, mLogout;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String userId, name, areaOfInterest, profileUserName, phone, profession
            , project, location, profileImageUrl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        mProfileImage = v.findViewById(R.id.profile_image);
        mProfileName = v.findViewById(R.id.profile_name);
        mProfileInterest = v.findViewById(R.id.profile_interest);
        mProfileUsername = v.findViewById(R.id.profile_username);
        mProfilePhoneNo = v.findViewById(R.id.profile_phone_no);
        mProfileProfession = v.findViewById(R.id.profile_profession);
        mProfileProjects = v.findViewById(R.id.profile_projects);
        mProfileLocation = v.findViewById(R.id.profile_location);
        mEditProfile = v.findViewById(R.id.edit_profile_btn);
        mLogout = v.findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        getUserInfo();

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
                return;
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(),ChooseLoginRegistration.class);
                startActivity(intent);
                return;
            }
        });
        return v;
    }
    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name")!= null){
                        name = map.get("name").toString();
                        mProfileName.setText(name);
                    }
                    if (map.get("Area of interest")!= null){
                        areaOfInterest = map.get("Area of interest").toString();
                        mProfileInterest.setText(areaOfInterest);
                    }
                    if (map.get("State")!= null){
                        location = map.get("State").toString();
                        mProfileLocation.getEditText().setText(location);
                    }
                    if (map.get("profileUserName")!= null){
                        profileUserName = map.get("profileUserName").toString();
                        mProfileUsername.getEditText().setText(profileUserName);
                    }
                    if (map.get("phone")!= null){
                        phone = map.get("phone").toString();
                        mProfilePhoneNo.getEditText().setText(phone);
                    }
                    if (map.get("projects")!= null){
                        project = map.get("projects").toString();
                        mProfileProjects.getEditText().setText(project);
                    }
                    if (map.get("profession")!= null){
                        profession = map.get("profession").toString();
                        mProfileProfession.getEditText().setText(profession);
                    }

                    Glide.clear(mProfileImage);
                    if (map.get("profileImageUrl")!= null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch (profileImageUrl){
                            case "default":
                                Glide.with(getActivity().getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getActivity().getApplication()).load(profileImageUrl).into(mProfileImage);
                                break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



