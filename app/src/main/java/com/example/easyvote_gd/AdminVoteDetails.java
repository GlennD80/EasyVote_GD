package com.example.easyvote_gd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AdminVoteDetails extends AppCompatActivity implements View.OnClickListener {

    private Button logoutAdmin;
    private Button voterList;
    private Button updateVoterDetails;
    private Button candidateList;
    private Button updateCandidateList;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private TextView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        logoutAdmin = (Button) findViewById(R.id.logoutAdmin);

        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminVoteDetails.this, MainActivity.class));
            }
        });

        voterList = (Button) findViewById(R.id.viewVotersList);

        voterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminVoteDetails.this, VoterList.class));
            }
        });

        updateVoterDetails = (Button) findViewById(R.id.updateVoterDetails);

        updateVoterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminVoteDetails.this, UpdateVoterDetails.class));
            }
        });

        candidateList = (Button) findViewById(R.id.viewCandidateList);

        candidateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminVoteDetails.this, CandidateList.class));
            }
        });

        updateCandidateList = (Button) findViewById(R.id.updateCandidateList);

        updateCandidateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminVoteDetails.this, CandidateList.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, AdminUser.class));
                break;
        }
    }
}