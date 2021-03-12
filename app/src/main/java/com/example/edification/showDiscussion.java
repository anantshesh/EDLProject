package com.example.edification;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.edification.adapters.questionsAdapter;
import com.example.edification.models.Questions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class showDiscussion extends Fragment {

    RecyclerView recyclerView;
    List<Questions> questions;
    questionsAdapter questionsAdapter;
    String myEmail, myUid;

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_discussion, container, false);

        recyclerView = view.findViewById(R.id.questionRecycler);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        checkCurrentUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        questions = new ArrayList<>();

        loadQuestions();


        return view;
    }

    private void loadQuestions() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Questions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Questions questions1 = ds.getValue(Questions.class);
                    questions.add(questions1);
                    questionsAdapter = new questionsAdapter(getContext(), questions);
                    recyclerView.setAdapter(questionsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkCurrentUser() {
        user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();



        }else{
            startActivity(new Intent(getContext(), Login.class));
        }
    }
}