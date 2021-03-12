package com.example.edification;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.HashMap;

public class uploadDiscussion extends Fragment {


    EditText question;
    Spinner department;
    Button post;
    String dept, email, uid, name;

    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;
    ProgressBar progressBar;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_upload_discussion, container, false);


        question = view.findViewById(R.id.question);
        department = view.findViewById(R.id.departmentType);
        post = view.findViewById(R.id.postquestionBt);
        progressBar = view.findViewById(R.id.postDisProg);

        pd = new ProgressDialog(getContext());

        ArrayAdapter<CharSequence> adapterCategoryOfItem = ArrayAdapter.createFromResource(getActivity(), R.array.Dept_type, android.R.layout.simple_spinner_item);
        adapterCategoryOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapterCategoryOfItem);
        dept = department.getSelectedItem().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        userDbRef = FirebaseDatabase.getInstance().getReference("User");
        Query query = userDbRef.orderByChild("Email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    name = "" + ds.child("Name").getValue();
                    email = "" + ds.child("Email").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Pquestion = question.getText().toString().trim();

                if(TextUtils.isEmpty(Pquestion)){
                    Toast.makeText(getActivity(), "Please enter your Question!", Toast.LENGTH_SHORT).show();
                }

                else{
                    uploadQuestion(Pquestion, dept);
                }
            }
        });

        return view;
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            email = user.getEmail();
            uid = user.getUid();
        }
        else{
            startActivity(new Intent(getContext(), Login.class));

        }
    }

    private void uploadQuestion(String pquestion, String department) {
        pd.setMessage("Posting...");
        pd.show();

        final String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("Uid", uid);
        hashMap.put("Email", email);
        hashMap.put("Name", name);
        hashMap.put("QuestionId", timeStamp);
        hashMap.put("Question", pquestion);
        hashMap.put("Department", department);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Questions");
        ref.child(timeStamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Question Posted!", Toast.LENGTH_SHORT).show();
                        question.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), "" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}