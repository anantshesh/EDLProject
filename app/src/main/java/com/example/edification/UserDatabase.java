package com.example.edification;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDatabase {

    public static String userId, userEmail, UserName;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseAuth auth;

//    public static boolean checkUserStatus(){
//
//        FirebaseUser user = auth.getCurrentUser();
//        if(user !=null){
//            userEmail = user.getEmail();
//            userId = user.getUid();
//        }
//        else
//        {
//           return true;
//        }
//
//    }



}
