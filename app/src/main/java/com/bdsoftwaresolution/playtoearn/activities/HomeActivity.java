package com.bdsoftwaresolution.playtoearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bdsoftwaresolution.playtoearn.R;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout taskoneLinear, tasktwoLinerar,taskthreeLinear;

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

    }
}
