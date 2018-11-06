package com.example.alhanoufaldawood.swe444;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class Log_in extends AppCompatActivity {

    EditText email, pass;
    Button Loginbtn;
    private FirebaseAuth auth;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

       //getSupportActionBar().setTitle("Home");
        getSupportActionBar().hide();


        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        Loginbtn = (Button) findViewById(R.id.login);


        Loginbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                btnLogin_Click(v);

            }


        });





    }


    public void btnLogin_Click(View view) {
       String mEmail = email.getText().toString();
        String mPass = pass.getText().toString();

        if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPass)) {

            if (TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(mPass)) {
                email.setError("Email is required");
                pass.setError("Password is required");
            }else if (TextUtils.isEmpty(mEmail)){
                email.setError("Email is required");

            }else
                pass.setError("Password is required");

        } else {
            auth.signInWithEmailAndPassword(mEmail, mPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent start = new Intent(Log_in.this, parentHome.class);
                                startActivity(start);
                            } else {
                                Toast.makeText(Log_in.this, "email or password is invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }











    }

    public void btnRegister_Click(View view) {
        Intent register = new Intent(Log_in.this, SignUp.class);
        startActivity(register);
    }


}
