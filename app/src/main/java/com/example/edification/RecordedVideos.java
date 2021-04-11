package com.example.edification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.edification.adapters.ScheduledVideosAdapter;
import com.example.edification.models.Videos;
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

public class RecordedVideos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Videos> videosArrayList = new ArrayList<Videos>();
    private ScheduledVideosAdapter scheduledVideosAdapter;

    Date currentDate = null;
    private SimpleDateFormat mdformat;

    String myUid, myEmail, dept;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference postreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_videos);

        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        recyclerView = findViewById(R.id.VideosRecycler);
        postreference = FirebaseDatabase.getInstance().getReference("Videos");

        firebaseAuth = FirebaseAuth.getInstance();


        checkCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        Query query = ref.orderByChild("Uid").equalTo(myUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    dept = "" + ds.child("Dept_type").getValue();

                    System.out.println("This is " + dept);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        loadVideos();

    }

    private void checkCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user !=null){
            myEmail = user.getEmail();
            myUid = user.getUid();
        }
        else
        {
            startActivity(new Intent(RecordedVideos.this, Login.class));
            finish();
        }
    }

    private void loadVideos() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecordedVideos.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Calendar c = Calendar.getInstance();
        try{
            currentDate = mdformat.parse(mdformat.format(c.getTime()));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Query query =  postreference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videosArrayList.clear();
                for(DataSnapshot ds : snapshot.getChildren() ){
                    Videos checkVideo = ds.getValue(Videos.class);
                    Calendar c = Calendar.getInstance();
                    Date startDate = null, endDate = null;
                    try{
                        currentDate = mdformat.parse(mdformat.format(c.getTime()));
                        String dd = checkVideo.getVideoPremierTime();
                        startDate = mdformat.parse(checkVideo.getVideoPremierTime());
                        System.out.println(startDate);
                        System.out.println(currentDate);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if(currentDate.compareTo(startDate) >= 0){
                        if (dept.equals(checkVideo.getDepartment())){
                            videosArrayList.add(checkVideo);
                            scheduledVideosAdapter = new ScheduledVideosAdapter(RecordedVideos.this, videosArrayList);
                            recyclerView.setAdapter(scheduledVideosAdapter);
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