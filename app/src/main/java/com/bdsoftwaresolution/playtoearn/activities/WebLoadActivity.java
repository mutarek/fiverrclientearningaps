package com.bdsoftwaresolution.playtoearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;

public class WebLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_load);
    }

    public void showvideoads(View view) {
        Toast.makeText(this, "Ads Not Loaded Yet", Toast.LENGTH_SHORT).show();
    }
}
