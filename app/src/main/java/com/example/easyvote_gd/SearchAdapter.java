package com.example.easyvote_gd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<String> fullNameList;
    ArrayList<String> addressList;
    ArrayList<String> ageList;
    ArrayList<String> emailList;
    ArrayList<String> uidList;

    /**
     *
     */
    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView fullName_Search, address_Search, age_Search, email_Search;
        Button updateVoterDetailsBtn;

        //search for data held the inflated views
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName_Search = (TextView) itemView.findViewById(R.id.fullName_Search);
            address_Search = (TextView) itemView.findViewById(R.id.address_Search);
            age_Search = (TextView) itemView.findViewById(R.id.age_Search);
            email_Search = (TextView) itemView.findViewById(R.id.email_Search);
            updateVoterDetailsBtn = (Button) itemView.findViewById(R.id.updateVoterDetailsBtn);
        }

        //btn click - sends bundle of data to next activity
        public void onClick(int position) {
            updateVoterDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateVoterDetails.class);
                    Bundle extras = new Bundle();

                    String fullName = fullNameList.get(position);
                    String address = addressList.get(position);
                    String age = ageList.get(position);
                    String email = emailList.get(position);

                    String uid = uidList.get(position);

                    extras.putString("name", fullName);
                    extras.putString("address", address);
                    extras.putString("age", age);
                    extras.putString("email", email);

                    extras.putString("uid", uid);

                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
        }
    }

    //lists of all voter details in firebase
    public SearchAdapter(Context context, ArrayList<String> fullNameList, ArrayList<String> addressList, ArrayList<String> ageList, ArrayList<String> emailList,  ArrayList<String> uidList) {
        this.context = context;
        this.fullNameList = fullNameList;
        this.addressList = addressList;
        this.ageList = ageList;
        this.emailList = emailList;
        this.uidList = uidList;
    }

    //group view and inflate the view holder
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    //binds the data to the view holder - name, address, age and email
    @Override
    public void onBindViewHolder (SearchViewHolder holder, int position) {
        holder.fullName_Search.setText(" Name: " + fullNameList.get(position));
        holder.address_Search.setText(" Address: " + addressList.get(position));
        holder.age_Search.setText(" Age: " + ageList.get(position));
        holder.email_Search.setText(" Email: " + emailList.get(position));
        holder.onClick(position);
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }
}
