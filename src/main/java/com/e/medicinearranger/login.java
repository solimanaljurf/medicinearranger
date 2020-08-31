package com.e.medicinearranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText ppass,pemail;
    Button btnsignin,btnlogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pemail = findViewById(R.id.editText);
        ppass  =   findViewById(R.id.editText4);
        btnsignin = findViewById(R.id.button2);
        btnlogin =findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = pemail.getText().toString().trim();
                String password = ppass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    pemail.setError("Email is Required.");

                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ppass.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    ppass.setError("Password Must be >= 6 Characters");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),medicine.class));
                        }else {
                            Toast.makeText(login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                        btnsignin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext(),signin.class));
                            }
                        });
                }


            });



            }

});}

    public void sign_one(View view) {
        startActivity(new Intent(getApplicationContext(),signin.class));
    }
}
