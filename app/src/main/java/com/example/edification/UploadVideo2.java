package com.example.edification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UploadVideo2 extends AppCompatActivity {

    private int current_year;
    private int current_month;
    private int current_date;
    private Button setTimeButton;
    private TextView year;
    private TextView month;
    private TextView date;
    private Spinner hours;
    private Spinner mins;
    private String mHours;
    private String mMins;
    private String mDept;
    private SimpleDateFormat mdformat;
    Date currentdate = null;
    public Date enteredStartDate = null;
    public Date enteredEndDate = null;
    private Spinner dept;
    EditText description;
    //LocalDate currentDate;
    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;

    String email, uid, name;


    private Button btn;
    private Button puch;
    private EditText title;
    private VideoView videoView;
    Uri contentURI = null;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video2);

        btn = (Button) findViewById(R.id.btn);
        videoView = (VideoView) findViewById(R.id.vv);
        puch = (Button) findViewById(R.id.push);
        title = findViewById(R.id.videotitle);
        description = findViewById(R.id.desc);
        setTimeButton = findViewById(R.id.set_time_button);

        mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        year =  findViewById(R.id.start_time_year);
        month =  findViewById(R.id.start_time_month);
        date =  findViewById(R.id.start_time_date);
        hours = findViewById(R.id.start_time_hours);
        mins =  findViewById(R.id.start_time_mins);

        dept = findViewById(R.id.dept);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Uploading video");
        progressDialog.setCanceledOnTouchOutside(false);



        ArrayAdapter<CharSequence> adapterdept = ArrayAdapter.createFromResource(UploadVideo2.this, R.array.Dept_type, android.R.layout.simple_spinner_item);
        adapterdept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterhours = ArrayAdapter.createFromResource(UploadVideo2.this, R.array.hours, android.R.layout.simple_spinner_item);
        adapterhours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adaptermins = ArrayAdapter.createFromResource(UploadVideo2.this, R.array.mins, android.R.layout.simple_spinner_item);
        adaptermins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dept.setAdapter(adapterdept);
        hours.setAdapter(adapterhours);
        mins.setAdapter(adaptermins);
        mDept = dept.getSelectedItem().toString();
        mHours = hours.getSelectedItem().toString();
        mMins = mins.getSelectedItem().toString();

        hours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mHours = hours.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMins = mins.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDept = dept.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        userDbRef = FirebaseDatabase.getInstance().getReference("User");
        Query query = userDbRef.orderByChild("Uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    name = "" + ds.child("Name").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                try {
                    currentdate = mdformat.parse(mdformat.format(c.getTime()));
                } catch (Exception c1) {
                    c1.printStackTrace();
                }
                current_date = c.get(Calendar.DAY_OF_MONTH);
                current_month = c.get(Calendar.MONTH);
                current_year = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UploadVideo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        year.setText(String.valueOf(i));
                        month.setText(String.valueOf(i1 + 1));
                        date.setText(String.valueOf(i2));
                    }
                }, current_year, current_month, current_date);
                datePickerDialog.show();
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        puch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredDateString = year.getText().toString() + "/" + month.getText().toString() + "/" + date.getText().toString()
                        + " " + mHours + ":" + mMins;
                try {
                    enteredStartDate = mdformat.parse(enteredDateString);
                    Calendar cal = Calendar.getInstance(); // creates calendar
                    cal.setTime(enteredStartDate); // sets calendar time/date
                    cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
                    System.out.println("checkff" + mdformat.format(cal.getTime()));
                    enteredEndDate = cal.getTime();
                } catch (Exception c) {
                    c.printStackTrace();
                }

                final String m_title = title.getText().toString().trim();
                final String m_description = description.getText().toString().trim();

                if (TextUtils.isEmpty(m_title)){
                    Toast.makeText(UploadVideo2.this, "Please enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(m_description)){
                    Toast.makeText(UploadVideo2.this, "Please enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentdate.compareTo(enteredStartDate) == 1) {
                    Toast.makeText(UploadVideo2.this, "Entered date is less than current date!", Toast.LENGTH_SHORT).show();
                    year.setText("0000");
                    month.setText("00");
                    date.setText("00");
                    return;
                }

                if (contentURI == null){
                    Toast.makeText(UploadVideo2.this, "Please select Video", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    uplaodData(m_title, m_description, String.valueOf(contentURI), String.valueOf(enteredStartDate), mDept);
                }



            }
        });

    }

    private void uplaodData(String m_title, String m_description, String uri, String premierDateTime, String dept) {
        progressDialog.show();

        final String timeStamp = String.valueOf(System.currentTimeMillis());

        String filePathAndName = "Videos/" + "video_" + timeStamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(filePathAndName);

        storageReference.putFile(Uri.parse(uri))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()){
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("VideoUrl", "" + downloadUri);
                            hashMap.put("VideoTitle", "" + m_title);
                            hashMap.put("VideoId", "" + timeStamp);
                            hashMap.put("TimeStamp", "" + timeStamp);
                            hashMap.put("VideoDesc", "" + m_description);
                            hashMap.put("VideoPremierTime", "" + premierDateTime);
                            hashMap.put("Department", "" + dept);

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Videos");
                            ref.child(timeStamp).setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UploadVideo2.this, "Video Uploaded", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(UploadVideo2.this, MainActivity2.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadVideo2.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadVideo2.this, ""
                        + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            email = user.getEmail();
            uid = user.getUid();
        }
        else{
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result",""+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what","cancle");
            return;
        }
        if (requestCode == GALLERY) {
            Log.d("what","gale");
            if (data != null) {
                contentURI = data.getData();

                String selectedVideoPath = getPath(contentURI);
                Log.d("path",selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);
                videoView.setVideoURI(contentURI);
                videoView.requestFocus();
                videoView.start();

            }

        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            videoView.setVideoURI(contentURI);
            videoView.requestFocus();
            videoView.start();
        }
    }

    private void saveVideoToInternalStorage (String filePath) {

        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

}