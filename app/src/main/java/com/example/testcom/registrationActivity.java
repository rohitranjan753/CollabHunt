package com.example.testcom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class registrationActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private Button mRegister;
    private TextInputLayout mEmail, mPassword, mName, mAge;
    private TextView mLinkLogin;

    private RadioGroup mRadioGroup;
    private Spinner mSpinnerInterest, mState, mInterestSubcategory;

    private String getInterest="";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mtoolbar = findViewById(R.id.tool_bar1);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(registrationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        mName = (TextInputLayout) findViewById(R.id.name);
        mAge = (TextInputLayout) findViewById(R.id.age);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mSpinnerInterest = (Spinner) findViewById(R.id.interest);
        mInterestSubcategory = (Spinner) findViewById(R.id.interest_subcategory);
        mState = (Spinner) findViewById(R.id.state);
        mEmail = (TextInputLayout) findViewById(R.id.email);
        mPassword = (TextInputLayout) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.register);
        mLinkLogin = (TextView) findViewById(R.id.linklogin);

        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registrationActivity.this, ChooseLoginRegistration.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        mInterestSubcategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getInterest.isEmpty()) {
                    Toast.makeText(registrationActivity.this, "Select your Area of interest first", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        List<String> listinterest = new ArrayList<String>();
        listinterest.add(0,"Select area of interest");
        listinterest.add("Education");
        listinterest.add("Music");
        listinterest.add("Travel");
        //listinterest.add("Fashion");
        //listinterest.add("Art");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listinterest);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerInterest.setAdapter(arrayAdapter);
        mSpinnerInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select area of interest")){
                    getInterest = "";
                    mInterestSubcategory.setAdapter(null);
                    mInterestSubcategory.setClickable(false);
                }
                else{
                    mInterestSubcategory.setClickable(true);
                    mSpinnerInterest.setSelection(position);
                    getInterest = mSpinnerInterest.getSelectedItem().toString();

                    if (getInterest.equals("Education"))
                    {
                        List<String> listEducation = new ArrayList<String>();
                        listEducation.add(0,"Select area of interest subcategory");
                        listEducation.add("History1");
                        listEducation.add("History2");
                        listEducation.add("History3");
                        listEducation.add("History4");
                        listEducation.add("History5");
                        listEducation.add("History6");
                        listEducation.add("History7");
                        listEducation.add("History8");
                        listEducation.add("History9");
                        listEducation.add("History10");
                        ArrayAdapter<String> educationArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listEducation);
                        educationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(educationArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Education category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    if (getInterest.equals("Music"))
                    {
                        List<String> listMusic = new ArrayList<String>();
                        listMusic.add(0,"Select area of interest subcategory");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        ArrayAdapter<String> musicArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listMusic);
                        musicArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(musicArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Music category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    if (getInterest.equals("Travel"))
                    {
                        List<String> listTravel = new ArrayList<String>();
                        listTravel.add(0,"Select area of interest subcategory");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        ArrayAdapter<String> travelArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listTravel);
                        travelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(travelArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Travel category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*

        mInterestSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getInterest == null || getInterest.isEmpty() || getInterest.equals("Select area of interest"))
                {
                    Toast.makeText(registrationActivity.this, "Select your Area of interest first",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (getInterest.equals("Education"))
                    {
                        List<String> listEducation = new ArrayList<String>();
                        listEducation.add(0,"SSelect area of interest subcategory");
                        listEducation.add("History1");
                        listEducation.add("History2");
                        listEducation.add("History3");
                        listEducation.add("History4");
                        listEducation.add("History5");
                        listEducation.add("History6");
                        listEducation.add("History7");
                        listEducation.add("History8");
                        listEducation.add("History9");
                        listEducation.add("History10");
                        ArrayAdapter<String> educationArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listEducation);
                        educationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(educationArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Education category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    if (getInterest.equals("Music"))
                    {
                        List<String> listMusic = new ArrayList<String>();
                        listMusic.add(0,"Select area of interest subcategory");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        listMusic.add("Music");
                        ArrayAdapter<String> musicArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listMusic);
                        musicArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(musicArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Music category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    if (getInterest.equals("Travel"))
                    {
                        List<String> listTravel = new ArrayList<String>();
                        listTravel.add(0,"SSelect area of interest subcategory");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        listTravel.add("Travel");
                        ArrayAdapter<String> travelArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listTravel);
                        travelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(travelArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Travel category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    if (getInterest.equals("Fashion"))
                    {
                        List<String> listFashion = new ArrayList<String>();
                        listFashion.add(0,"Select area of interest subcategory");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        listFashion.add("listFashion");
                        ArrayAdapter<String> fashionArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listFashion);
                        fashionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(fashionArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Fashion category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    if (getInterest.equals("Art"))
                    {
                        List<String> listArt = new ArrayList<String>();
                        listArt.add(0,"Select area of interest subcategory");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        listArt.add("listArt");
                        ArrayAdapter<String> artArrayAdapter = new ArrayAdapter<String>(registrationActivity.this, android.R.layout.simple_spinner_item, listArt);
                        artArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mInterestSubcategory.setAdapter(artArrayAdapter);
                        mInterestSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Select Art category")){
                                }
                                else{
                                    mInterestSubcategory.setSelection(position);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }
        });

         */

        List<String> liststate = new ArrayList<String>();
        liststate.add(0,"Where are you from");
        liststate.add("Andaman and Nicobar Island");
        liststate.add("Andhra Pradesh");
        liststate.add("Arunachal Pradesh");
        liststate.add("Assam");
        liststate.add("Bihar");
        liststate.add("Chandigarh");
        liststate.add("Chhattisgarh");
        liststate.add("Dadra and Nagar Haveli and Daman and Diu");
        liststate.add("Delhi");
        liststate.add("Goa");
        liststate.add("Gujarat");
        liststate.add("Haryana");
        liststate.add("Himachal Pradesh");
        liststate.add("Jammu and Kashmir");
        liststate.add("Jharkhand");
        liststate.add("Karnataka");
        liststate.add("Kerala");
        liststate.add("Ladakh");
        liststate.add("Lakshadweep");
        liststate.add("Madhya Pradesh");
        liststate.add("Maharashtra");
        liststate.add("Manipur");
        liststate.add("Meghalaya");
        liststate.add("Mizoram");
        liststate.add("Nagaland");
        liststate.add("Odisha");
        liststate.add("Puducherry");
        liststate.add("Punjab");
        liststate.add("Rajasthan");
        liststate.add("Sikkim");
        liststate.add("Tamil Nadu");
        liststate.add("Telangana");
        liststate.add("Tripura");
        liststate.add("Uttar Pradesh");
        liststate.add("Uttarakhand");
        liststate.add("West Bengal");
        ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,liststate);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(stateArrayAdapter);
        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Where are you from")){
                }
                else{
                    mState.setSelection(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = (RadioButton) findViewById(selectedId);

                final String name = mName.getEditText().getText().toString();
                final String age = mAge.getEditText().getText().toString();
                final String getSelectedInterest = mSpinnerInterest.getSelectedItem().toString();
                final String getState = mState.getSelectedItem().toString();
                final String email = mEmail.getEditText().getText().toString();
                final String password = mPassword.getEditText().getText().toString();
                final String interestSubcategory = mInterestSubcategory.getSelectedItem().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(registrationActivity.this, "All fields must be filled!",Toast.LENGTH_SHORT).show();
                }
                else if(getSelectedInterest.equals("Select area of interest")){
                    Toast.makeText(registrationActivity.this, "Interest not selected",Toast.LENGTH_SHORT).show();
                }
                else if(interestSubcategory.equals("Select area of interest subcategory")){
                    Toast.makeText(registrationActivity.this, "Interest subcategory not selected",Toast.LENGTH_SHORT).show();
                }
                else if(getState.equals("Where are you from")){
                    Toast.makeText(registrationActivity.this, "State not selected",Toast.LENGTH_SHORT).show();
                }
                else if (password.length() <6){
                    Toast.makeText(registrationActivity.this, "Password must be atleast 6 characters.",Toast.LENGTH_SHORT).show();
                }
                else if(mRadioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(registrationActivity.this, "Radio Button Must be Checked",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(registrationActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                            } else {
                                String userId = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                                Map userInfo = new HashMap<>();
                                userInfo.put("name", name);
                                userInfo.put("age", age);
                                userInfo.put("sex", radioButton.getText().toString());
                                userInfo.put("Area of interest", getSelectedInterest);
                                userInfo.put("State", getState);
                                userInfo.put("profileImageUrl","default");
                                userInfo.put("email", email);
                                userInfo.put("subInterest", interestSubcategory);

                                currentUserDb.updateChildren(userInfo);

                            }
                        }
                    });
                }
            }
        });
        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registrationActivity.this,loginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}