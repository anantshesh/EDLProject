package com.example.edification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class UploadVideo extends AppCompatActivity {


//    private ActionBar actionBar;
//
//    private EditText title;
//    private VideoView videoView;
//    private Button uplaodVideo;
//    FloatingActionButton pickVideo;
//
//    private String videoTitle;
//
//
//    private static final int VIDEO_PICK_GALLERY_CODE = 100;
//    private static final int VIDEO_PICK_CAMERA_CODE = 101;
//    private static final int CAMERA_REQUEST_CODE = 102;
//
//    private String[] cameraPermission;
//
//     Uri videoUri = null;
//
//    private ProgressDialog progressDialog;
//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_video);
//
//        actionBar = getSupportActionBar();
//
//        actionBar.setTitle("Add Video");
//
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//
//        title = findViewById(R.id.videoTitle);
//        videoView = findViewById(R.id.video);
//        uplaodVideo = findViewById(R.id.uploadVideo);
//        pickVideo = findViewById(R.id.pickVideo);
//
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Please wait");
//        progressDialog.setMessage("Uploading video");
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission
//                .WRITE_EXTERNAL_STORAGE};
//
//
//        pickVideo.setOnClickListener(v -> videoPickDialog());
//
//
//        uplaodVideo.setOnClickListener(v -> {
//
//            videoTitle = title.getText().toString().trim();
//            if (TextUtils.isEmpty(videoTitle)) {
//                Toast.makeText(UploadVideo.this, "Please enter Title",
//                        Toast.LENGTH_SHORT).show();
//            } else if (videoUri == null) {
//                Toast.makeText(UploadVideo.this, "Please add video", Toast.LENGTH_SHORT).show();
//            } else {
//                uploadVideo(title, String.valueOf(videoUri));
//                System.out.println(videoUri);
//
//
//            }
//
//        });


    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return super.onSupportNavigateUp();
//    }


//    private void videoPickDialog() {
//        String[] option = {"Camera", "Gallery"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Pick Video from ")
//                .setItems(option, (dialog, which) -> {
//                    if (which == 0) {
//                        if (!checkCameraPermission()) {
//                            requestCameraPermission();
//                        } else {
//                            videoPickCamera();
//
//                        }
//                    } else if (which == 1) {
//                        videoPickGallery();
//
//
//                    }
//                });
//        builder.show();
//    }
//
//    private void requestCameraPermission() {
//        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
//    }
//
//    private boolean checkCameraPermission() {
//        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.
//                permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
//        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.
//                permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED;
//
//        return result1 && result2;
//    }
//
//
//
//    private void setVideoToVideoView() {
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
////      mediaController.setMediaPlayer(videoView);
//        videoView.setMediaController(mediaController);
//        videoView.setVideoURI(this.videoUri);
//        videoView.requestFocus();
//        videoView.setOnPreparedListener(mp -> {
//            videoView.start();
//
//        });
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case CAMERA_REQUEST_CODE:
//                if (grantResults.length > 0) {
//                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    if (cameraAccepted && storageAccepted) {
//                        videoPickCamera();
//                    } else {
//                        Toast.makeText(this, "Camera & Storage permission required",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == RESULT_OK) {
//            if (requestCode == VIDEO_PICK_GALLERY_CODE) {
//                videoUri = data.getData();
//                setVideoToVideoView();
//
//
//            } else if (requestCode == VIDEO_PICK_CAMERA_CODE) {
//                videoUri = data.getData();
//                setVideoToVideoView();
//
//
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void videoPickGallery() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("video/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, VIDEO_PICK_GALLERY_CODE);
//
//
//    }
//
//    private void videoPickCamera() {
//
//        ContentValues cv = new ContentValues();
//        cv.put(MediaStore.Video.Media.TITLE, "Temp Pick");
//        cv.put(MediaStore.Video.Media.DESCRIPTION, "Temp Desc");
//        videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cv);
//
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
//        startActivityForResult(intent, VIDEO_PICK_CAMERA_CODE);
//
//
////        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
////        startActivityForResult(intent, VIDEO_PICK_CAMERA_CODE);
//    }
//
//    private void uploadVideo(EditText title, String videoUri) {
//
//
//        progressDialog.show();
//
//        String timeStamp = "" + System.currentTimeMillis();
//
//        String filePathAndName = "Videos/" + "video_" + timeStamp;
//
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(filePathAndName);
//
//        storageReference.putFile(Uri.parse(videoUri))
//                .addOnSuccessListener(taskSnapshot -> {
//                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                    while (!uriTask.isSuccessful()) ;
//                    Uri downloadUri = uriTask.getResult();
//                    if (uriTask.isSuccessful()) {
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("VideoTitle", "" + videoTitle);
//                        hashMap.put("TimeStamp", "" + timeStamp);
//                        hashMap.put("VideoId", "" + timeStamp);
//                        hashMap.put("VideoUrl", "" + downloadUri);
//
//                        DatabaseReference reference = FirebaseDatabase.getInstance().
//                                getReference("Videos");
//                        reference.child(timeStamp)
//                                .setValue(hashMap)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(UploadVideo.this,
//                                                "Video Uploaded!", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(UploadVideo.this, MainActivity2.class));
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(UploadVideo.this, ""
//                                                + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    progressDialog.dismiss();
//                    Toast.makeText(UploadVideo.this, ""
//                            + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                });
//    }

}