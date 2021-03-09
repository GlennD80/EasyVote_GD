package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCandidateVotes extends AppCompatActivity {

    //FirebaseDatabase database;
    //DatabaseReference reference;
    private Button updateVoteCount;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private String userID;

    ArrayList<PieEntry> candidates = new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidate_votes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateVoteCount = findViewById(R.id.updateVotesChart);

        PieChart pieChart = findViewById(R.id.pieChart);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Profiles");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int voteCountVal;
                String voteNameVal;

                for(DataSnapshot candidate : dataSnapshot.getChildren()) {

                    DataSnapshot voteCount = candidate.child("count");
                    DataSnapshot candidateName = candidate.child("name");

                    voteCountVal = Integer.parseInt(voteCount.getValue().toString());
                    voteNameVal = candidateName.getValue().toString();

                    candidates.add(new PieEntry(voteCountVal, voteNameVal));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //getElectionResults();



        ArrayList<PieEntry> candidates = new ArrayList<>();
        candidates.add(new PieEntry(10, "Mary Lou MacDonald"));
        candidates.add(new PieEntry(10, "Leo Varakar"));
        candidates.add(new PieEntry(10, "Micheal Martin"));

        PieDataSet pieDataSet = new PieDataSet(candidates, "Candidates");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Candidates");
        pieChart.animate();

        updateVoteCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getElectionResults();
            }
        });

    //}

/*    public void getElectionResults () {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int voteCountVal;
                String voteNameVal;

                for(DataSnapshot candidate : dataSnapshot.getChildren()) {

                    DataSnapshot voteCount = candidate.child("count");
                    DataSnapshot candidateName = candidate.child("name");

                    voteCountVal = Integer.parseInt(voteCount.getValue().toString());
                    voteNameVal = candidateName.getValue().toString();

                    candidates.add(new PieEntry(voteCountVal, voteNameVal));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


    }
}