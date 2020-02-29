package com.bdsoftwaresolution.playtoearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WithdrawActivity extends AppCompatActivity {
    private EditText number, amount;
    private RadioGroup radioGroup;
    private RadioButton bksh, rocket, nagad;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String cUsers = firebaseAuth.getCurrentUser().getUid();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firestore.collection("Users");
    private ProgressDialog progressDialog;
    int tk, newtk;
    String samount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        number = findViewById(R.id.withdrawnumberet);
        amount = findViewById(R.id.withdrawAmmountER);
        radioGroup = findViewById(R.id.radio_group);
        bksh = findViewById(R.id.radio_bkash);
        rocket = findViewById(R.id.radio_rocket);
        nagad = findViewById(R.id.radio_nagad);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait....");
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
            }
        });


    }

    public void withdrawBTN(View view) {
        checkuserCoin();
    }

    private void checkuserCoin() {
        collectionReference.document(cUsers).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        tk = Integer.valueOf(String.valueOf(document.get("Coin")));

                        if (tk <= 600) {
                            Toast.makeText(WithdrawActivity.this, "Not Enough Coin", Toast.LENGTH_SHORT).show();
                        } else {
                            checkNullValue();
                        }

                    } else {

                    }
                } else {
                    Toast.makeText(WithdrawActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WithdrawActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDB() {
        newtk = tk - Integer.valueOf(samount);
        collectionReference.document(cUsers).update("tk", newtk).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(WithdrawActivity.this, "Successfuly Update", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WithdrawActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkNullValue() {
        String snumber = number.getText().toString();
        samount = amount.getText().toString();
        int selectedID = radioGroup.getCheckedRadioButtonId();
        if (snumber.isEmpty()) {
            Toast.makeText(this, "Number is required", Toast.LENGTH_SHORT).show();
        } else if (samount.isEmpty()) {
            Toast.makeText(this, "Ammount is required", Toast.LENGTH_SHORT).show();
        } else if (selectedID == -1) {
            Toast.makeText(this, "Please select a method", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton radioButton = radioGroup.findViewById(selectedID);
            String method = radioButton.getText().toString();
            progressDialog.show();
            makePayment(snumber, samount, method);
        }


    }

    private void makePayment(String snumber, String samount, String method) {
        Map<String, Object> map = new HashMap<>();
        map.put("Withdraw_Number", snumber);
        map.put("Withdraw_Amount", samount);
        map.put("Withdrawal_Method", method);
        collectionReference.document(cUsers).collection("Withdraw").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressDialog.dismiss();
                Toast.makeText(WithdrawActivity.this, "You Will get payment within 24 hours", Toast.LENGTH_SHORT).show();
                updateDB();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(WithdrawActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
