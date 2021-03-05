package com.example.edification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText email, password, name;
    TextView LoginHere;
    Button signup;
    ProgressBar progressBar;
    Spinner dept, user;
    String dtype, utype, _name;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.SignUpEmail);
        password = findViewById(R.id.SignUpPassword);
        name = findViewById(R.id.SignUpName);
        LoginHere = findViewById(R.id.LoginSignUpNav);
        signup = findViewById(R.id.SignUpButton);
        progressBar = findViewById(R.id.SignUpProgress);

        firebaseAuth = FirebaseAuth.getInstance();

        dept = findViewById(R.id.departmentType);
        ArrayAdapter<CharSequence> adapterCategoryOfItem = ArrayAdapter.createFromResource(
                SignUp.this, R.array.Dept_type, android.R.layout.simple_spinner_item);
        adapterCategoryOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(adapterCategoryOfItem);

        user = findViewById(R.id.userType);
        ArrayAdapter<CharSequence> adapterCategoryOfItem1= ArrayAdapter.createFromResource(
                SignUp.this, R.array.User_type, android.R.layout.simple_spinner_item);
        adapterCategoryOfItem1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user.setAdapter(adapterCategoryOfItem1);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _email = email.getText().toString().trim();
                String _pass = password.getText().toString().trim();
                 _name = name.getText().toString().trim();
                 dtype = dept.getSelectedItem().toString();
                 utype = user.getSelectedItem().toString();

                if(TextUtils.isEmpty(_email)){
                    email.setError("Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(_pass)){
                    password.setError("Enter Password");
                    return;
                }
                if (TextUtils.isEmpty(_name)){
                    name.setError("Enter Name");
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(_email, _pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user1 = firebaseAuth.getCurrentUser();

                            String _email1 = user1.getEmail();
                            String _uid = user1.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("Email", _email1);
                            hashMap.put("Uid", _uid);
                            hashMap.put("Name", _name);;
                            hashMap.put("Dept_type", dtype);
                            hashMap.put("User_type", utype);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("User");

                            reference.child(_uid).setValue(hashMap);

                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, MainActivity2.class));

                        }
                        else {
                            Toast.makeText(SignUp.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        LoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(SignUp.this, Login.class);
                startActivity(i1);
            }
        });
    }
}