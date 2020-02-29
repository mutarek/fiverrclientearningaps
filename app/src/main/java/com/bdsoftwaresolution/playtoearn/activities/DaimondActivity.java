package com.bdsoftwaresolution.playtoearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DaimondActivity extends AppCompatActivity {

    String scode;
    private String qr = "XYZCB56";
    private EditText code;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String cuser = firebaseAuth.getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    int oldcoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daimond);
        code = findViewById(R.id.code_et);
        initOldCoin();
    }

    private void initOldCoin() {
        collectionReference.document(cuser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        oldcoin = Integer.parseInt(String.valueOf(document.get("Coin")));
                    } else {
                        Toast.makeText(DaimondActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DaimondActivity.this, "get failed with", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void matchthetext(View view) {
        scode = code.getText().toString();
        if (scode.isEmpty()) {
            Toast.makeText(this, "Please enter the code", Toast.LENGTH_SHORT).show();
        } else {
            checkMatching();
        }
    }

    private void checkMatching() {
        if (scode.matches(qr)) {
            addCoin();
        } else {
            Toast.makeText(this, "Please Enter correct code", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCoin() {
        db.collection("Users").document(cuser).update(
                "Coin", oldcoin + 10, "Last_Task_Time", 11)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DaimondActivity.this, "coin added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
