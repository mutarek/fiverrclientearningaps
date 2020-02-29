package com.bdsoftwaresolution.playtoearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextView login;
    private EditText emeial, pass;
    private Button button;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        casting();
    }

    private void casting() {
        emeial = findViewById(R.id.signUpEmailET);
        pass = findViewById(R.id.signUpPasswprdET);
        login = findViewById(R.id.gotoLogin);
        button = findViewById(R.id.signupBTN);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checknullValue();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void checknullValue() {
        String semail = emeial.getText().toString();
        String spass = pass.getText().toString();
        if (semail.isEmpty()) {
            Toast.makeText(this, "Please Enter your email", Toast.LENGTH_SHORT).show();
        } else if (spass.isEmpty()) {
            Toast.makeText(this, "Password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            createuser(semail, spass);
        }
    }

    private void createuser(final String semail, String spass) {
        firebaseAuth.createUserWithEmailAndPassword(semail, spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Successfully account created", Toast.LENGTH_SHORT).show();
                    uploadDB(semail);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDB(String semail) {
        String cuser = firebaseAuth.getCurrentUser().getUid();
        Map<String,Object> map = new HashMap<>();
        map.put("E_mail",semail);
        map.put("Coin",0);
        db.collection("Users").document(cuser).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    gotomain();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gotomain() {
        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
           gotomain();
        } else {

        }
    }
}
