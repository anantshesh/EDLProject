package com.example.edification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edification.adapters.answerAdapter;
import com.example.edification.models.Answers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class indidualQuestions extends AppCompatActivity {

    RecyclerView recyclerView;

    TextView name, email, time, question, department;
    EditText ans;
    Button ansbt;
    String questionId, Uname, Qtime, Uemail, dept, Uid, quest;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Answers> answersList = new ArrayList<Answers>();
    private answerAdapter answerAdapter;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indidual_questions);

        recyclerView = findViewById(R.id.answerRecycler);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        time = findViewById(R.id.time);
        question = findViewById(R.id.question);
        department = findViewById(R.id.dept);
        ans = findViewById(R.id.answer);
        ansbt = findViewById(R.id.ansbt);

        pd =  new ProgressDialog(this);

        Intent intent = getIntent();
        questionId = intent.getStringExtra("question");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Questions");

        Query query = databaseReference.orderByChild("QuestionId").equalTo(questionId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Uid = "" + ds.child("Uid").getValue();
                    Uname = "" + ds.child("Name").getValue();
                    quest = "" + ds.child("Question").getValue();
                    Qtime = "" + ds.child("QuestionId").getValue();
                    Uemail = "" + ds.child("Email").getValue();
                    dept = "" + ds.child("Department").getValue();

                    name.setText(Uname);
                    email.setText(Uemail);
                    question.setText(quest);
                    department.setText(dept);

                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeInMillis(Long.parseLong(Qtime));

                    String qTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa", calendar);

                    time.setText(qTime);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ansbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAns();
            }
        });

        loadAnswer();

    }

    private void loadAnswer() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(indidualQuestions.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        answersList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Answers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                answersList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Answers check = ds.getValue(Answers.class);
                    if(check.getQuestionId().equals(questionId)){
                        answersList.add(check);
                        answerAdapter = new answerAdapter(indidualQuestions.this, answersList);
                        recyclerView.setAdapter(answerAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void submitAns() {
        pd.setMessage("Submitting your Answer");
        String answer = ans.getText().toString().trim();
        if(TextUtils.isEmpty(answer)){
            Toast.makeText(this, "Please Enter an Answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            String timeStamp = String.valueOf(System.currentTimeMillis());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Answers");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("Answer", answer);
            hashMap.put("AnsId", timeStamp);
            hashMap.put("QuestionId", Qtime);
            hashMap.put("AnswererName", Uname);
            hashMap.put("AnswererEmail", Uemail);


            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(indidualQuestions.this, "Answer Submitted", Toast.LENGTH_SHORT).show();
                            ans.setText("");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(indidualQuestions.this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}