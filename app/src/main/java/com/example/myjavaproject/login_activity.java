package com.example.myjavaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://payroll-2ddfe-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginbtn=findViewById(R.id.loginbtn);
        //ImageButton googlebtn=findViewById(R.id.googlebtn);
        final EditText email=findViewById(R.id.email);
        final EditText password=findViewById(R.id.password);
        Intent signup= new Intent(login_activity.this, Signup.class);
        TextView signupbtn=findViewById(R.id.signupbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailtxt=email.getText().toString();
                final String passwordtxt=password.getText().toString();
                String encodedemail=encodeEmail(emailtxt);
                if(emailtxt.isEmpty()||passwordtxt.isEmpty()){
                    Toast.makeText(login_activity.this, "Please Enter Username or Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(encodedemail)) {
                                final String getPassword = snapshot.child(encodedemail).child("Password").getValue(String.class);
                                if (getPassword != null && getPassword.equals(passwordtxt)) {
                                    Toast.makeText(login_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent home=new Intent(login_activity.this, HomePage.class);
                                    startActivity(home);
                                } else {
                                    Toast.makeText(login_activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(login_activity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(login_activity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signup);
            }
        });


    }
    private String encodeEmail(String email) {
        return email.replace(".", ",").replace("@", "_");
    }
}