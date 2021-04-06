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
    private Button updateVoteCountBtn;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private String userID;

    ArrayList<PieEntry> candidates = new ArrayList<>();
    PieChart pieChart;

    DatabaseReference profilesFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidate_votes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateVoteCountBtn = findViewById(R.id.updateVotesChart);

        pieChart = findViewById(R.id.pieChart);

        profilesFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Profiles");

        //btn click updates pie data and clears current data
        updateVoteCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                candidates.clear();
                getElectionResults();
            }
        });
    }

    /**
     * get firebase count and names values for each candidate
     */
    public void getElectionResults () {

        profilesFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
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


                //PieDataSet pieDataSet = new PieDataSet(candidates, "Candidates");

                /**
                 * set pie data
                 */
                PieDataSet pieDataSet = new PieDataSet(candidates, "");

                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(16f);

                PieData pieData = new PieData(pieDataSet);

                pieChart.setUsePercentValues(true);

                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Candidates");
                pieChart.animate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}