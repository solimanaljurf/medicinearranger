package com.e.medicinearranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


//import Viewholder.MedicineView;

public class home extends AppCompatActivity {
    RecyclerView recyclerView ;
    DatabaseReference refmed;
    ArrayList<UserHelper> list;
    MyAdabter adabter;
    UserHelper helper;
    FloatingActionButton addmedic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.ryclermed);
        addmedic = findViewById(R.id.addmed);
        addmedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,medicine.class));
            }
        });
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refmed = FirebaseDatabase.getInstance().getReference("Medicine");
        list = new ArrayList<UserHelper>();
        helper =new UserHelper();
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        refmed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                     helper=dataSnapshot1.getValue(UserHelper.class);
                        list.add(helper);
                    }
                    adabter = new MyAdabter(home.this, list);

                    recyclerView.setAdapter(adabter);
                    adabter.notifyDataSetChanged();
                }else{
                    Toast.makeText(home.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(home.this, "Opsss...Somthing is wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            list.remove(viewHolder.getAdapterPosition());
            FirebaseDatabase.getInstance().getReference("Medicine").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                    }


                }

            });

            adabter.notifyDataSetChanged();
        }
    };

    }


