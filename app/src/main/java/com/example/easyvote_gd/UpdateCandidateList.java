package com.example.easyvote_gd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateCandidateList extends AppCompatActivity {

    EditText addCandidateName;
    EditText addCandidateParty;
    EditText addCandidateLocation;
    Button addNewCandidate;

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
}