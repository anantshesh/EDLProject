package com.example.edification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.edification.adapters.videosAdapter;
import com.example.edification.models.Videos;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    View navView;

    private FirebaseAuth firebaseAuth;
    String myUid, myEmail, typeUser;

    private RecyclerView recyclerView;
    private ArrayList<Videos> videosArrayList;
    private videosAdapter videosAdapter;

    Date currentDate = null;
    private SimpleDateFormat mdformat;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        firebaseAuth = FirebaseAuth.getInstance();

        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity2.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        navView = navigationView.getHeaderView(0);

        recyclerView = findViewById(R.id.VideosRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity2.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        loadVideos();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            UserMenuSelectced(menuItem);
            return false;
        });

        checkCurrentUser();

        //Sample change

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        Query query = ref.orderByChild("Uid").equalTo(myUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    typeUser = "" + ds.child("User_type").getValue();

                    System.out.println("This is " + typeUser);

                    if(typeUser.equals("Student")){
                        hideItem();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadVideos() {
        videosArrayList = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        try{
            currentDate = mdformat.parse(mdformat.format(c.getTime()));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videosArrayList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Videos videos = ds.getValue(Videos.class);
                    Calendar c = Calendar.getInstance();
                    Date startDate = null, endDate = null;

                    for(int videoS = 0; videoS <videosArrayList.size(); videoS++){
                        try{
                            startDate = mdformat.parse(videosArrayList.get(videoS).getVideoPremierTime());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        if (currentDate.compareTo(startDate) >= 0){
                            videosArrayList.remove(videoS);
                            videosAdapter = new videosAdapter(MainActivity2.this, videosArrayList);
                            recyclerView.setAdapter(videosAdapter);
                        }
                    }

//                    videosArrayList.add(videos);
//                    videosAdapter = new videosAdapter(MainActivity2.this, videosArrayList);
//                    recyclerView.setAdapter(videosAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Videos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videosArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Videos videos = ds.getValue(Videos.class);
                    Calendar c = Calendar.getInstance();
                    Date startDate = null;
                    try{
                        startDate = mdformat.parse(videos.getVideoPremierTime());
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    System.out.println("Checkoff " + startDate);
                    System.out.println("Current time" + currentDate);
                    if (currentDate.compareTo(startDate) <= 0){
                        videosArrayList.add(videos);
                        videosAdapter = new videosAdapter(MainActivity2.this, videosArrayList);
                        recyclerView.setAdapter(videosAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hideItem() {
        navigationView = findViewById(R.id.navigation_view);
        Menu nav_manu = navigationView.getMenu();
        nav_manu.findItem(R.id.videoUpload).setVisible(false);
        nav_manu.findItem(R.id.uploadStudyMaterial).setVisible(false);
    }

    private void checkCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user !=null){
            myEmail = user.getEmail();
            myUid = user.getUid();
        }
        else
        {
            startActivity(new Intent(MainActivity2.this, Login.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        checkCurrentUser();
        super.onResume();
    }



    private void UserMenuSelectced(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                break;

            case R.id.nav_notification:
                startActivity(new Intent(getApplicationContext(), Notification.class));
                break;

            case R.id.nav_discussion:
                startActivity(new Intent(getApplicationContext(), Discussion.class));
                break;

            case R.id.nav_recorded_videos:
                startActivity(new Intent(getApplicationContext(), RecordedVideos.class));
                break;

            case R.id.nav_subjects:
                startActivity(new Intent(getApplicationContext(), Subjects.class));
                break;

            case R.id.videoUpload:
                startActivity(new Intent(getApplicationContext(), UploadVideo2.class));
                break;

            case R.id.uploadStudyMaterial:
                startActivity(new Intent(getApplicationContext(), UploadStudyMaterial.class));
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}