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

public class AdminMainMenu extends AppCompatActivity implements View.OnClickListener {

    private Button logoutAdmin;
    private Button voterList;
    private Button updateVoterDetails;
    private Button candidateList;
    private Button updateCandidateList;
    private Button viewCandidateVotes;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private TextView banner;

    /**
     * admin user main menu details
     * @param
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        logoutAdmin = (Button) findViewById(R.id.logoutAdmin);

        //logout admin user from activity/app
        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminMainMenu.this, MainActivity.class));
            }
        });

        voterList = (Button) findViewById(R.id.viewVotersList);

        //navigate to voter list activity
        voterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainMenu.this, VoterList.class));
            }
        });

        updateVoterDetails = (Button) findViewById(R.id.updateVoterDetails);

        //navigate to search voter details activity
        updateVoterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainMenu.this, SearchVoterDetails.class));
            }
        });

        candidateList = (Button) findViewById(R.id.viewCandidateList);

        //navigate to candidate list activity
        candidateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainMenu.this, CandidateList.class));
            }
        });

        updateCandidateList = (Button) findViewById(R.id.updateCandidateList);

        //navigate to update candidate list activity
        updateCandidateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainMenu.this, UpdateCandidateList.class));
            }
        });

        viewCandidateVotes = (Button) findViewById(R.id.candidateVotes);

        //navigate to view candidate voters activity
        viewCandidateVotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainMenu.this, ViewCandidateVotes.class));
            }
        });

    }

    //easy vote banner navigate back
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, AdminUserDetails.class));
                break;
        }
    }
}