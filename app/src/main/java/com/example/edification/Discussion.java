package com.example.edification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Discussion extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

       BottomNavigationView navigationView = findViewById(R.id.discussionBottomNav);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        showDiscussion frag1 = new showDiscussion();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.discussioncontainer, frag1, "");
        ft1.commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.showDiscussion:
                            showDiscussion frag1 = new showDiscussion();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.discussioncontainer, frag1, "");
                            ft1.commit();
                            return true;
                        case R.id.uploadDiscussion:
                            uploadDiscussion frag2 = new uploadDiscussion();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.discussioncontainer, frag2, "");
                            ft2.commit();
                            return true;
                    }
                    return false;
                }
            };
}