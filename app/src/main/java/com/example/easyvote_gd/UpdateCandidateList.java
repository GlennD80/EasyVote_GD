package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UpdateCandidateList extends AppCompatActivity {

    EditText addCandidateName;
    EditText addCandidateParty;
    EditText addCandidateLocation;
    Button addNewCandidate;
    ImageView addCandidatePic;
    int TAKE_IMAGE_CODE = 10001;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_candidate_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addCandidateName = (EditText) findViewById(R.id.add_Candidate_Name);
        addCandidateParty = (EditText) findViewById(R.id.add_Candidate_Party);
        addCandidateLocation = (EditText) findViewById(R.id.add_Candidate_Location);
        addNewCandidate = (Button) findViewById(R.id.add_Candidate_Btn);
        addCandidatePic = (ImageView) findViewById(R.id.imageCandidate);

        reference = FirebaseDatabase.getInstance().getReference().child("Profiles");

        addNewCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewCandidate();
            }
        });

    }

    private void insertNewCandidate() {

        String name = addCandidateName.getText().toString().trim();
        String party = addCandidateParty.getText().toString().trim();
        String location = addCandidateLocation.getText().toString().trim();

        NewCandidate newCandidate = new NewCandidate(name, party, location);

        reference.push().setValue(newCandidate);

        Toast.makeText(UpdateCandidateList.this, "New Candidate has been inserted", Toast.LENGTH_LONG).show();
    }

    public void handleImageClick(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) !=null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                    addCandidatePic.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("name")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    private void getDownloadUrl (StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrI(uri);
                    }
                });
    }

    private void setUserProfileUrI (Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
            .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateCandidateList.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateCandidateList.this, "Profile Image Fail", Toast.LENGTH_LONG).show();
                    }
                });

    }
}