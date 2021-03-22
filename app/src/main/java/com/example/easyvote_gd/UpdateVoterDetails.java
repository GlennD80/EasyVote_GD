package com.example.easyvote_gd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateVoterDetails extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView txtSearch;

    private TextView fullNameUpd;
    private TextView addressUpd;
    private TextView emailUpd;
    private TextView ageUpd;

    private TextView newFullNameUpd;
    private TextView newAddressUpd;
    private TextView newAgeUpd;

    public String fullName;
    public String address;
    public String age;
    public String email;

    public Button updateDet;

    DatabaseReference reference;
    FirebaseDatabase mFire;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_voter_details);

        mAuth = FirebaseAuth.getInstance();

        //reference = FirebaseDatabase.getInstance().getReference().child("Users");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameUpd = (TextView) findViewById(R.id.fullNameUpdate);
        addressUpd = (TextView) findViewById(R.id.addressUpdate);
        ageUpd = (TextView) findViewById(R.id.ageUpdate);
        emailUpd = (TextView) findViewById(R.id.emailUpdate);
        updateDet = (Button) findViewById(R.id.updateDet);

        newFullNameUpd = (EditText) findViewById(R.id.enterFullNameUpdate);
        newAddressUpd = (EditText) findViewById(R.id.enterAddressUpdate);
        newAgeUpd = (EditText) findViewById(R.id.enterAgeUpdate);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        fullName = extras.getString("name");
        fullNameUpd.setText(" Name: " + fullName);

        address = extras.getString("address");
        addressUpd.setText(" Address: " + address);

        age = extras.getString("age");
        ageUpd.setText(" Age: " + age);

        email = extras.getString("email");
        emailUpd.setText(" Email: " + email);

       updateDet.setOnClickListener(this);
    }

    private void updateUser() {

        String newFullName = newFullNameUpd.getText().toString().trim();
        String newAddress = newAddressUpd.getText().toString().trim();
        String newAge = newAgeUpd.getText().toString().trim();

        if(newFullName.isEmpty()){
            newFullNameUpd.setError("Full Name is Required");
            newFullNameUpd.requestFocus();
            return;
        }

        if(newAddress.isEmpty()){
            newAddressUpd.setError("Address is Required");
            newAddressUpd.requestFocus();
            return;
        }

        if(newAge.isEmpty()){
            newAgeUpd.setError("Age is Required");
            newAgeUpd.requestFocus();
            return;
        }
    }

    private boolean isNameChanged() {

        if(!fullName.equals(newFullNameUpd.getText().toString())){

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference newName = ref.child("Users").child("uidList");


            newName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    dataSnapshot.getRef().child("name").setValue(newFullNameUpd.getText().toString().trim());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //reference = FirebaseDatabase.getInstance().getReference().child("Users");
           //

/*            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid();
                    .child("name").setValue(newFullNameUpd.getText().toString().trim());*/

/*            DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
            dbRef.child("Users").child(email);
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", newFullNameUpd.getText().toString());

            dbRef.updateChildren(updates);*/


            //fullName = newFullNameUpd.getText().toString();
            return true;
        }
        return false;
    }

    private boolean isAddressChanged() {
        if(!address.equals(newAddressUpd.getText().toString())) {
            return true;
        }
        return false;
    }

    private boolean isAgeChanged() {
        if(!age.equals(newAgeUpd.getText().toString())) {

            return true;
        }
        return false;
    }

    public void onClick (View view) {
        updateUser();
        if(isNameChanged() || isAddressChanged() || isAgeChanged()) {
            //reference.child("Users").child(email).child("name").setValue(newFullNameUpd.getText().toString().trim());
            //reference.child(email).child("address").setValue(newAddressUpd.getText().toString().trim());
            //reference.child(email).child("age").setValue(newAgeUpd.getText().toString().trim());

            //FirebaseDatabase.getInstance().getReference("Users").setValue(newFullNameUpd.getText().toString().trim());

        Toast.makeText(UpdateVoterDetails.this, "Details have been updated", Toast.LENGTH_SHORT).show();
        } else {
           Toast.makeText(UpdateVoterDetails.this, "Error updating details", Toast.LENGTH_SHORT).show();
        }

    }
}
