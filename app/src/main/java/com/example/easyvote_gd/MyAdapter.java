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

    //get candidate details by position
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.party.setText(profiles.get(position).getParty());
        holder.location.setText(profiles.get(position).getLocation());
        holder.voteBtn.setText(profiles.get(position).getVoteBtn());
        holder.onClick(position);

        //image display
        Glide.with(holder.profilePic.getContext())
                .load(profiles.get(position).getProfilePic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profilePic);
    }


    @Override
    public int getItemCount() { return profiles.size();
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

        //btn click sends candidate details to next activity to confirm vote
        public void onClick (int position)
        {
            voteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ConfirmVote.class);
                    Bundle extras = new Bundle();

                    String name = profiles.get(position).getName();
                    String uid = profiles.get(position).getUid();

                    extras.putString("name", name);
                    extras.putString("uid", uid);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
        }
    }
}
