package com.e.medicinearranger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class medicine extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText namemed;
    EditText qauntit;
    EditText meddose;
    private Spinner spcolor, sptype,beforeaf;
    Button totimm;
    FirebaseDatabase medsave;
    DatabaseReference medsrefrance;
    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        namemed = findViewById(R.id.nammed);
        meddose = findViewById(R.id.dose);
        qauntit = findViewById(R.id.qauntitytxt);
        sptype = findViewById(R.id.spinner1);
        spcolor = findViewById(R.id.spinner2);
        beforeaf = findViewById(R.id.spinner3);
        totimm = findViewById(R.id.butmed);
        medsave = FirebaseDatabase.getInstance();
        medsrefrance = medsave.getReference("Medicine");
        final FirebaseUser userKey = FirebaseAuth.getInstance().getCurrentUser();
        userId = userKey.getUid();





        totimm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = namemed.getText().toString();
                String doses = meddose.getText().toString();
                String qauntett = qauntit.getText().toString();
                String spinnertype = sptype.getSelectedItem().toString();
                String spinnercolour = spcolor.getSelectedItem().toString();
                String beforeafter =beforeaf.getSelectedItem().toString();



                UserHelper userHelper = new UserHelper();
                userHelper.setName(name);
                userHelper.setDoses(doses);
                userHelper.setQauntett(qauntett);
                userHelper.setSpinnertype(spinnertype);
                userHelper.setSpinnercolour(spinnercolour);
                userHelper.setBeforAfter(beforeafter);

                medsrefrance.child(userId).setValue(userHelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(medicine.this).edit();
                            preferences.putString("name",name);
                            preferences.putString("doses",doses);
                            preferences.putString("beforAfter",beforeafter);
                            preferences.apply();
                            startActivity(new Intent(getApplicationContext(), time.class));

                        }
                        else
                            Toast.makeText(medicine.this, "Fail saving data", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });




        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typemedicine, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.colormed, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 =findViewById(R.id.spinner3);
        ArrayAdapter<String>adapter2 =new ArrayAdapter<>(
                this,
                R.layout.coustom_spinner,
                getResources().getStringArray(R.array.beforeafter)
        );
        adapter2.setDropDownViewResource(R.layout.costum_spinner_dropdown);
        spinner3.setAdapter(adapter2);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }






    public void btn_home(View view) {
        startActivity(new Intent(getApplicationContext(), home.class));
    }
}


