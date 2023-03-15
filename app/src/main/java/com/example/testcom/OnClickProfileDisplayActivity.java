package com.example.testcom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnClickProfileDisplayActivity extends AppCompatActivity {

    private String storeUid;
    private DatabaseReference onClickDb;
    private CircleImageView mProfileImage;
    private TextView mProfileName, mProfileInterest, mProfileUsername;
    private TextInputLayout mProfileEmail, mProfilePhoneNo
            , mProfileProfession, mProfileProjects
            , mProfileLocation;
    private FirebaseAuth mAuth;
    private String name, areaOfInterest, UserName, phone, profession
            , project, location, profileImageUrl, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_profile_display);
        mProfileImage = (CircleImageView) findViewById(R.id.profile_image_display);
        mProfileName = (TextView) findViewById(R.id.profile_name_display);
        mProfileInterest = (TextView) findViewById(R.id.profile_interest_display);
        mProfileUsername = (TextView) findViewById(R.id.profile_user_name_display);
        mProfileEmail = (TextInputLayout) findViewById(R.id.profile_email_display);
        mProfilePhoneNo = (TextInputLayout) findViewById(R.id.profile_phone_no_display);
        mProfileProfession = (TextInputLayout) findViewById(R.id.profile_profession_display);
        mProfileProjects = (TextInputLayout) findViewById(R.id.profile_projects_display);
        mProfileLocation = (TextInputLayout) findViewById(R.id.profile_location_display);
        storeUid = getIntent().getExtras().getString("uid");
        onClickDb = FirebaseDatabase.getInstance().getReference().child("Users").child(storeUid);
        onClickDb.addValueEventListener(new ValueEventListener() {
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
                        UserName = map.get("profileUserName").toString();
                        mProfileUsername.setText(UserName);
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
                    if (map.get("email")!= null){
                        userEmail = map.get("email").toString();
                        mProfileEmail.getEditText().setText(userEmail);
                    }

                    Glide.clear(mProfileImage);
                    if (map.get("profileImageUrl")!= null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch (profileImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mProfileImage);
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