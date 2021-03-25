package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterVoter extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private TextView banner, registerUser;
    private EditText editTextFullName, editTextAge, editTextAddress, editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.signIn);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.regUserName);
        editTextAge = (EditText) findViewById(R.id.regUserAge);
        editTextAddress = (EditText) findViewById(R.id.regUserConst);
        editTextPassword = (EditText) findViewById(R.id.regUserPassword);
        editTextEmail = (EditText) findViewById(R.id.regUserEmail);

    }

    @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.banner:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.signIn:
                    registerUser();
                    break;
            }
        }

    private void registerUser() {

        //input values for register user
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        //name return if not empty
        if(fullName.isEmpty()){
            editTextFullName.setError("Full Name is Required");
            editTextFullName.requestFocus();
            return;
        }

        //age return if not empty
        if(age.isEmpty()){
            editTextAge.setError("Age is Required");
            editTextAge.requestFocus();
            return;
        }

        //address return if not empty
        if(address.isEmpty()){
            editTextAddress.setError("Address is Required");
            editTextAddress.requestFocus();
            return;
        }

        //email return if not empty
        if(email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        //validate email pattern
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        //password return if not empty
        if(password.isEmpty()) {
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        //validate password length
        if(password.length()< 6){
            editTextPassword.setError("Password Requires at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        /**
         * register user operation
         */
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(address, age, email, fullName);

                    //parse string value to int
                    int ageNumber = Integer.parseInt(age);

                    //if user under 18 - user cannot register
                    if (ageNumber <= 17) {
                        Toast.makeText(RegisterVoter.this, "User must be 18 and above to register", Toast.LENGTH_SHORT).show();
                    } else {

                        //if user is 18 and above user can register
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                //register user if task successful
                                if(task.isSuccessful()) {
                                    Toast.makeText(RegisterVoter.this, "User Registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterVoter.this, "Failed to register, Try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegisterVoter.this, "Failed to register", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}