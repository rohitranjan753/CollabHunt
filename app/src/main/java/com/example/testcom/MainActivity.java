package com.example.testcom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {

    //Initializing variables
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning Variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        //add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_chat));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_person));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //initialize fragment
                Fragment fragment = null;

                //check condition
                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new SearchFragment();
                        break;
                    case 3:
                        fragment = new ChatFragment();
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        break;
                    }
                    //load fragment
                    loadFragment(fragment);
                }
            });

            //Set Notification Count
            bottomNavigation.setCount(3,"10");
            //Set home fragment initially selected
            bottomNavigation.show(1,true);
            bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
                @Override
                public void onClickItem(MeowBottomNavigation.Model item) {
                    //Display toast
                    /*Toast.makeText(getApplicationContext()
                            ,"You Clicked" + item.getId()
                            ,Toast.LENGTH_SHORT).show();*/
                }
            });
            bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
                @Override
                public void onReselectItem(MeowBottomNavigation.Model item) {
                    //Display toast
                    /*Toast.makeText(getApplicationContext()
                            ,"You Reselected" + item.getId()
                            ,Toast.LENGTH_SHORT).show();*/
                }
            });
        }

    private void loadFragment(Fragment fragment) {
        //replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}