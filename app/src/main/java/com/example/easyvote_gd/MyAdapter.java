package com.example.easyvote_gd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private DatabaseReference mDatabase;
    TextView mDisplaySummary;

    Context context;
    ArrayList<Profile> profiles;

    public MyAdapter(Context c, ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.voteview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.party.setText(profiles.get(position).getParty());
        holder.location.setText(profiles.get(position).getLocation());
        holder.voteBtn.setText(profiles.get(position).getVoteBtn());
        holder.onClick(position);

        Glide.with(holder.profilePic.getContext())
                .load(profiles.get(position).getProfilePic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, party, location;
        ImageView profilePic;
        Button voteBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.can_name);
            party = (TextView) itemView.findViewById(R.id.party);
            location = (TextView) itemView.findViewById(R.id.location);
            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
            voteBtn = (Button) itemView.findViewById(R.id.voteBtn);
        }

        public void onClick (int position)
        {
            voteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ConfirmVote.class);
                    Bundle extras = new Bundle();

                    String name = profiles.get(position).getName();
                    extras.putString("name", name);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                    //String party

                    //String userID =

                    //Toast.makeText(context, position+ "Thank you for your vote", Toast.LENGTH_LONG).show();
/*                    Toast.makeText(context,"Thank you for your vote", Toast.LENGTH_LONG).show();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Profiles").child("Candidate1").child("count");
                    //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Profiles").child("count");

                    //clicked = true;
                    //voteBtn.setVisibility(View.INVISIBLE);

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long voteCounts = (long) dataSnapshot.getValue();
                            mDatabase.setValue(voteCounts + 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(context,"Database Error", Toast.LENGTH_LONG).show();
                        }
                    });*/

                }
            });
        }
    }
}
