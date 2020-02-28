package com.bdsoftwaresolution.playtoearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bdsoftwaresolution.playtoearn.R;

public class LoginActivity extends AppCompatActivity {

    private TextView signuptv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        casting();
    }

    private void casting() {
        signuptv = findViewById(R.id.goforsignup);
        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }
}
