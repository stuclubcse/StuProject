package com.example.mini.stupro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private ImageView imgbtn;
    EditText t1, t2, t3, t4;
    private FirebaseAuth fauth;
    Button btn;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fauth = FirebaseAuth.getInstance();

        imgbtn = (ImageView) findViewById(R.id.closebtn);
        t1 = (EditText) findViewById(R.id.username);
        t2 = (EditText) findViewById(R.id.password);
        t3 = (EditText) findViewById(R.id.phonenumber);
        t4 = (EditText) findViewById(R.id.name);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btn = (Button) findViewById(R.id.regbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(register.this, "Loading", Toast.LENGTH_LONG).show();

                final String email = t1.getText().toString().trim();
                final String password = t2.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = fauth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = mDatabase.child(user_id);
             current_user_db.setValue(true);
                            current_user_db.child("Name").setValue(t4.getText().toString());
                            current_user_db.child("Phone Number").setValue(t3.getText().toString());
                            Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent regIntent = new Intent(register.this, join.class);
                            regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(regIntent);
                        }
                    });
                } else {
                    Toast.makeText(register.this, "COMPLETE ALL FIELDS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}










