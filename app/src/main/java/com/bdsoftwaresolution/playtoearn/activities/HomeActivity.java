package com.bdsoftwaresolution.playtoearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout taskoneLinear, tasktwoLinerar, taskthreeLinear, withdraw;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String cuser = firebaseAuth.getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private TextView coinNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        casting();
    }

    private void casting() {
        taskoneLinear = findViewById(R.id.taskOneID);
        tasktwoLinerar = findViewById(R.id.taskTwoID);
        taskthreeLinear = findViewById(R.id.taskThreeID);
        withdraw = findViewById(R.id.taskFourID);
        coinNow = findViewById(R.id.total_earning);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, WithdrawActivity.class));
            }
        });
        taskthreeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DaimondActivity.class));
            }
        });
        tasktwoLinerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, WebLoadActivity.class));
            }
        });
        taskoneLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SpinActivity.class));
            }
        });
        initallValue();

    }

    private void initallValue() {
        collectionReference.document(cuser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String currentcoin = document.get("Coin").toString();
                        coinNow.setText("Coin: " +currentcoin);
                    } else {
                        Toast.makeText(HomeActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "get failed with", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
