package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.UUID;

public class UpdateCandidateList extends AppCompatActivity {

    EditText addCandidateName;
    EditText addCandidateParty;
    EditText addCandidateLocation;
    Button addNewCandidate;
    ImageView addCandidatePic;
    int TAKE_IMAGE_CODE = 10001;

    DatabaseReference candidateRefFirebase;
    private String candidatePicUri;

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

        //firebase ref to profiles table with candidate details
        candidateRefFirebase = FirebaseDatabase.getInstance().getReference().child("Profiles");

        //btn click to add new candidate
        addNewCandidate.setOnClickListener(new View.OnClickListener() {
            @Override

            //run insert new candidate method from button click
            public void onClick(View v) {
                insertNewCandidate();
            }
        });
    }

    /**
     * get current voter details and enter and add new details to firebase
     */
    private void insertNewCandidate() {

        String name = addCandidateName.getText().toString().trim();
        String party = addCandidateParty.getText().toString().trim();
        String location = addCandidateLocation.getText().toString().trim();
        String profilePic = candidatePicUri;

        //add new candidate details and push to firebase
        NewCandidate newCandidate = new NewCandidate(name, party, location, profilePic);
        candidateRefFirebase.push().setValue(newCandidate);

    }

    //btn click for camera option
    public void handleImageClick(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) !=null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    //image OK and set image
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

    //sting value the pseudo randomly generate uid for new candidate
    final String randomKey = UUID.randomUUID().toString();

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        //ref upload image to firebase path
        StorageReference storageRefImage = FirebaseStorage.getInstance().getReference().child("images/" + randomKey);

        //image ref and upload
        storageRefImage.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(storageRefImage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    // add image and set uri - url details
    private void getDownloadUrl (StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        candidatePicUri = uri.toString();
                        setUserProfileUrI(uri);
                    }
                });
    }

    /**
     * image added and validation
     * @param uri
     */
    private void setUserProfileUrI (Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateCandidateList.this, "Candidate updated Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateCandidateList.this, "Profile update Fail", Toast.LENGTH_LONG).show();
                    }
                });

    }
}