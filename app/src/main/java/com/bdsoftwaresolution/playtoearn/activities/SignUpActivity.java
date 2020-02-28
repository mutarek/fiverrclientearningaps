package com.bdsoftwaresolution.playtoearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bdsoftwaresolution.playtoearn.R;

public class SignUpActivity extends AppCompatActivity {

    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        casting();
    }

    private void casting() {
        login= findViewById(R.id.gotoLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }

    public void gotomain(View view) {
        startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
    }
}
