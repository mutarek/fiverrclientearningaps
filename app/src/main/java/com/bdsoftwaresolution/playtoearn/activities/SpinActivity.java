package com.bdsoftwaresolution.playtoearn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bdsoftwaresolution.playtoearn.R;

import java.util.Random;

public class SpinActivity extends AppCompatActivity {
    ImageView imageView_wheel;
    ImageButton imageButton_spin;
    int degree = 0;
    int degree_old = 0;
    Random r;
    int score = 0;
    public static final float FACTOR = 15f;
    TextView textView;
    int oldcoin;
    String currentuser;
    String current_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        imageView_wheel = findViewById(R.id.wheel);
        imageButton_spin = findViewById(R.id.button);
        textView = findViewById(R.id.textview);
       /* firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        prevoiusref = FirebaseDatabase.getInstance().getReference().child("Users");*/
        loadoldcoin();
        getSupportActionBar().hide();
        current_score = currentNumber(360 - (degree % 360));
        r = new Random();
        /*adView = new AdView(this, "492301937997089_492304621330154", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();*/
        imageButton_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;

                RotateAnimation rotateAnimation = new RotateAnimation(degree_old, degree,
                        RotateAnimation.RELATIVE_TO_SELF, .5f,
                        RotateAnimation.RELATIVE_TO_SELF, .5f);


                rotateAnimation.setDuration(3600);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());


                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        textView.setText("score");

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setText(currentNumber(360 - (degree % 360)));
                        imageButton_spin.setVisibility(View.GONE);
                        int n = oldcoin + score;
                        //databaseReference.child(currentuser).child("Point").setValue(n);
                        new CountDownTimer(20000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                Toast.makeText(SpinActivity.this, "Button Enable After: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                            }


                            public void onFinish() {
                                imageButton_spin.setVisibility(View.VISIBLE);

                            }

                        }.start();
                        diaglog();


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView_wheel.startAnimation(rotateAnimation);


            }
        });


    }

    private void loadoldcoin() {
        /*prevoiusref.child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                oldcoin = Integer.valueOf(dataSnapshot.child("Point").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }


    private String currentNumber(int degree) {

        String text = "";


        if (degree >= (FACTOR * 1) && degree < (FACTOR * 3)) {

            text = "2";

            score = score + 2;

        }


        if (degree >= (FACTOR * 3) && degree < (FACTOR * 5)) {

            text = "3";
            score = score + 3;
        }

        if (degree >= (FACTOR * 5) && degree < (FACTOR * 7)) {

            text = "10";
            score = score + 10;
        }

        if (degree >= (FACTOR * 7) && degree < (FACTOR * 9)) {

            text = "5";
            score = score + 5;

        }

        if (degree >= (FACTOR * 9) && degree < (FACTOR * 11)) {

            text = "6";
            score = score + 6;
        }

        if (degree >= (FACTOR * 11) && degree < (FACTOR * 13)) {

            text = "7";
            score = score + 7;
        }

        if (degree >= (FACTOR * 13) && degree < (FACTOR * 15)) {

            text = "8";
            score = score + 8;
        }

        if (degree >= (FACTOR * 15) && degree < (FACTOR * 17)) {

            text = "9";
            score = score + 9;
        }

        if (degree >= (FACTOR * 17) && degree < (FACTOR * 19)) {

            text = "100";
            score = score + 100;
        }

        if (degree >= (FACTOR * 19) && degree < (FACTOR * 21)) {

            text = "11";
            score = score + 11;
        }

        if (degree >= (FACTOR * 21) && degree < (FACTOR * 23)) {

            text = "12";
            score = score + 12;
        }

        if (degree >= (FACTOR * 23) && degree < (360) || degree >= 0 && degree < (FACTOR * 1)) {

            text = "0 point";

        }

        return text;


    }

    public void diaglog() {
        final Dialog dialog = new Dialog(SpinActivity.this);
        dialog.setContentView(R.layout.custom_dialouge);
        Button dialogButton = dialog.findViewById(R.id.cool_id);
        TextView textView = dialog.findViewById(R.id.dialog_score_id);
        String a = currentNumber(360 - (degree % 360));

        textView.setText(a + " " + "Points");


        // if button is clicked, close the custom dialog

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });
        dialog.show();

    }

    @Override
    public void finish() {
        Intent intent = new Intent(SpinActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
