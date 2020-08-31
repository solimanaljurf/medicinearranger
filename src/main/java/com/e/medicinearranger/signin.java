package com.e.medicinearranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signin extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText pname,pemail,ppass;
    Button btnsignin;
    TextView logintext;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        pname = findViewById(R.id.editTextname);
        pemail = findViewById(R.id.editTextphone);

        ppass = findViewById(R.id.editTextpass);
        logintext = findViewById(R.id.loginbut);
        btnsignin = findViewById(R.id.signupbut);


        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String email = pemail.getText().toString().trim();
                String password = ppass.getText().toString().trim();
                final String fullName = pname.getText().toString();


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


                if (TextUtils.isEmpty(fullName)) {
                    pname.setError("Name is Required.");
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signin.this, "User Created.", Toast.LENGTH_SHORT).show();

                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);

                                }
                            }).addOnFailureListener (new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),medicine.class));


                        } else {
                            Toast.makeText(signin.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }



                    }
                });
                logintext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),login.class));



            }
        });
    }});}

    public void login_one(View view) {
        startActivity(new Intent(getApplicationContext(),login.class));
    }
}
















