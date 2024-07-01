package com.example.myjavaproject;

import static android.Manifest.permission.INTERNET;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Signup extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://payroll-2ddfe-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            // Check if the permission is not granted


        });


        final EditText username = findViewById(R.id.username);
        final EditText phoneno = findViewById(R.id.phoneno);

        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText repassword = findViewById(R.id.repassword);
        Button registerbtn = findViewById(R.id.registerbtn);
        final TextView loginbtn = findViewById(R.id.loginbtn);


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernametxt = username.getText().toString();
                final String phonetxt = phoneno.getText().toString();
                final String emailtxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();
                final String repasswordtxt = repassword.getText().toString();

                  if (usernametxt.isEmpty() || emailtxt.isEmpty() || passwordtxt.isEmpty() || repasswordtxt.isEmpty() || phonetxt.isEmpty()) {
                    Toast.makeText(Signup.this, "Please Complete all the fields above", Toast.LENGTH_SHORT).show();
                  }
                  else if (!passwordtxt.equals(repasswordtxt)) {
                    Toast.makeText(Signup.this, "Passwords doesnot match", Toast.LENGTH_SHORT).show();
                  }
                  else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(encodeEmail(emailtxt)) ){
                                Toast.makeText(Signup.this, "User is Already Registered", Toast.LENGTH_SHORT).show();
                            } else {
                                String encodedEmail = encodeEmail(emailtxt);
                                databaseReference.child("Users").child(encodedEmail).child("Name").setValue(usernametxt);
                                databaseReference.child("Users").child(encodedEmail).child("Password").setValue(passwordtxt);
                                //databaseReference.child("Users").child(phonetxt).child("email").setValue(emailtxt);
                                Toast.makeText(Signup.this, "User Registered Succesfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                  }
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public boolean checkpermission(){
       int resultinternet= ActivityCompat.checkSelfPermission(this,INTERNET);
       return resultinternet== PackageManager.PERMISSION_GRANTED;
    }
    private String encodeEmail(String email) {
        return email.replace(".", ",").replace("@", "_");
    }
}

