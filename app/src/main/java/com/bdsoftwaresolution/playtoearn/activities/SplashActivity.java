package com.bdsoftwaresolution.playtoearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;

public class SplashActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isConnected();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("No Internet");
        progressDialog.setMessage("Please turn on Internet");
        progressDialog.setCancelable(false);
    }

    public void isConnected() {
        new CheckNetworkConnection(this, new CheckNetworkConnection.OnConnectionCallback() {

            @Override
            public void onConnectionSuccess() {
                upDateUi();
            }

            @Override
            public void onConnectionFail(String msg) {
                Toast.makeText(SplashActivity.this, "Please Trun on your Internet Connection \\n\" +\n" +
                        "                        \"We Can't Work Without Internet", Toast.LENGTH_SHORT).show();
                progressDialog.show();
            }
        }).execute();

    }

    private void upDateUi() {
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                finish();

            }

        }.start();

    }


}
